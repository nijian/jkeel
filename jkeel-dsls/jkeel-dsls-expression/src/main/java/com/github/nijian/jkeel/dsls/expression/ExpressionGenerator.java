package com.github.nijian.jkeel.dsls.expression;

import java.io.IOException;
import java.io.InputStream;

import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.ExecuteMethodInjector;
import com.github.nijian.jkeel.dsls.injectors.ClassInjector;
import com.github.nijian.jkeel.dsls.injectors.ConstructorInjector;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExpressionGenerator {

  private final static Logger logger = LoggerFactory.getLogger(ExpressionGenerator.class);

  private final static String EXPR_INTERNAL_NAME = Type.getInternalName(Expression.class);

  public static void inject(ExpressionMeta meta, MethodVisitor methodVisitor, InputStream dsl) {
    try {
      injectByInput(meta, methodVisitor, CharStreams.fromStream(dsl));
    } catch (IOException e) {
      logger.error("Fail to construct char stream by input stream", e);
      throw new RuntimeException(e);
    } finally {
      if (dsl != null) {
        try {
          dsl.close();
        } catch (Exception e) {
          logger.warn("Fail to close dsl input stream");
        }
      }
    }
  }

  public static void inject(ExpressionMeta meta, MethodVisitor methodVisitor, String dsl) {
    injectByInput(meta, methodVisitor, CharStreams.fromString(dsl));
  }

  private static void injectByInput(ExpressionMeta meta, MethodVisitor methodVisitor, CharStream input) {
    InjectorExecutor injectorExecutor = new InjectorExecutor();
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(new ExpressionInjection(injectorExecutor, methodVisitor, meta), tree);
  }

  public static byte[] generateClass(ExpressionMeta meta, InputStream dsl) {
    try {
      return generateClassByInput(meta, CharStreams.fromStream(dsl));
    } catch (IOException e) {
      logger.error("Fail to construct char stream by input stream", e);
      throw new RuntimeException(e);
    } finally {
      if (dsl != null) {
        try {
          dsl.close();
        } catch (Exception e) {
          logger.warn("Fail to close dsl input stream");
        }
      }
    }
  }

  public static byte[] generateClass(ExpressionMeta meta, String dsl) {
    return generateClassByInput(meta, CharStreams.fromString(dsl));
  }

  private static byte[] generateClassByInput(ExpressionMeta meta, CharStream input) {

    InjectorExecutor injectorExecutor = new InjectorExecutor();
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    // class
    String retTypeSignature = Utility.getSignature(meta.getRetType().getName());
    String exprSignature = String.format(Const.EXP_SIGNATURE_TEMPLATE, retTypeSignature);
    injectorExecutor.execute(new ClassInjector(classWriter, Opcodes.V1_8, Opcodes.ACC_PUBLIC, meta.getName(),
        exprSignature, EXPR_INTERNAL_NAME, null));
    // constructor
    injectorExecutor.execute(new ConstructorInjector(classWriter, EXPR_INTERNAL_NAME));
    // execute method
    injectorExecutor.execute(new ExecuteMethodInjector(classWriter, tree, walker, meta));

    return classWriter.toByteArray();
  }

  private static ParseTree genTree(CharStream input) {
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    return parser.inject();
  }

}