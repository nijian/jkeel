package com.github.nijian.jkeel.dsls.expression;

import java.io.IOException;
import java.io.InputStream;

import com.github.nijian.jkeel.dsls.Generator;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionGenerator extends Generator {

  private static Logger logger = LoggerFactory.getLogger(ExpressionGenerator.class);

  public static void inject(MethodVisitor methodVisitor, InputStream dsl) {
    try {
      injectByInput(methodVisitor, CharStreams.fromStream(dsl));
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

  public static void inject(MethodVisitor methodVisitor, String dsl) {
    injectByInput(methodVisitor, CharStreams.fromString(dsl));
  }

  public static byte[] generateClass(String uri, InputStream dsl) {
    try {
      return generateClassByInput(uri, CharStreams.fromStream(dsl));
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

  public static byte[] generateClass(String uri, String dsl) {
    return generateClassByInput(uri, CharStreams.fromString(dsl));
  }

  private static void injectByInput(MethodVisitor methodVisitor, CharStream input) {
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();

    walker.walk(new ExpressionInjection(methodVisitor), tree);

  }

  private static byte[] generateClassByInput(String uri, CharStream input) {
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, uri,
        "Lcom/github/nijian/jkeel/dsls/expression/Expression<Ljava/lang/Integer;>;", Const.EXP, null);
    injectConstruct(classWriter, Const.EXP);

    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)Ljava/lang/Integer;",
        "(Lorg/apache/commons/jxpath/JXPathContext;)Ljava/lang/Integer;", null);
    walker.walk(new ExpressionInjection(methodVisitor), tree);
    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;",
        false);
    methodVisitor.visitInsn(Opcodes.ARETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)Ljava/lang/Object;",
        "(Lorg/apache/commons/jxpath/JXPathContext;)Ljava/lang/Object;", null);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, uri, "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)Ljava/lang/Integer;", false);
    methodVisitor.visitInsn(Opcodes.ARETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    return classWriter.toByteArray();
  }

  private static ParseTree genTree(CharStream input) {
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    return parser.inject();
  }

}