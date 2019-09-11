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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionGenerator {

  private static Logger logger = LoggerFactory.getLogger(ExpressionGenerator.class);

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

  public static byte[] generateClass(ExpressionMeta meta, String uri, InputStream dsl) {
    try {
      return generateClassByInput(meta, uri, CharStreams.fromStream(dsl));
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

  public static byte[] generateClass(ExpressionMeta meta, String uri, String dsl) {
    return generateClassByInput(meta, uri, CharStreams.fromString(dsl));
  }

  private static void injectByInput(ExpressionMeta meta, MethodVisitor methodVisitor, CharStream input) {
    InjectorExecutor injectorExecutor = new InjectorExecutor();
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(new ExpressionInjection(injectorExecutor, methodVisitor), tree);

  }

  private static byte[] generateClassByInput(ExpressionMeta meta, String uri, CharStream input) {

    String operandTypeSignature = Utility.getSignature(meta.getOperandType().getName());
    String exprSignature = String.format(Const.EXP_SIGNATURE_TEMPLATE, operandTypeSignature);
    String executeMethodSignature = String.format(Const.EXECUTE_SIGNATURE_TEMPLATE, operandTypeSignature);

    InjectorExecutor injectorExecutor = new InjectorExecutor();
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    // class
    injectorExecutor
        .execute(new ClassInjector(classWriter, Opcodes.V1_8, Opcodes.ACC_PUBLIC, uri, exprSignature, Const.EXP, null));
    // constructor
    injectorExecutor.execute(new ConstructorInjector(classWriter, Const.EXP));
    // execute method
    injectorExecutor.execute(new ExecuteMethodInjector(classWriter, tree, walker, executeMethodSignature));

    return classWriter.toByteArray();
  }

  private static ParseTree genTree(CharStream input) {
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    return parser.inject();
  }

}