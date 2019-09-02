package com.github.nijian.jkeel.dsls.expression;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExpressionGenerator {

  public static byte[] generate(String uri, String dsl) {

    CodePointCharStream input = CharStreams.fromString(dsl);
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    ParseTree tree = parser.inject();

    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, uri, null, Const.EXP, null);

    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, Const.EXP, "<init>", "()V", false);
    methodVisitor.visitInsn(Opcodes.RETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)I", null, null);
    walker.walk(new ExpressionInjection(methodVisitor), tree);
    methodVisitor.visitInsn(Opcodes.IRETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    return classWriter.toByteArray();
  }

}