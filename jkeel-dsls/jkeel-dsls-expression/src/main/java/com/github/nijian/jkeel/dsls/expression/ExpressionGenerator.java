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

  public static byte[] generate(String uri, InputStream dsl) {
    try {
      return generateByInput(uri, CharStreams.fromStream(dsl));
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

  public static byte[] generate(String uri, String dsl) {
    return generateByInput(uri, CharStreams.fromString(dsl));
  }

  private static byte[] generateByInput(String uri, CharStream input) {
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    ParseTree tree = parser.inject();

    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, uri, null, Const.EXP, null);

    injectConstruct(classWriter, Const.EXP);

    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)I", null, null);
    walker.walk(new ExpressionInjection(methodVisitor), tree);
    methodVisitor.visitInsn(Opcodes.IRETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    return classWriter.toByteArray();
  }

}