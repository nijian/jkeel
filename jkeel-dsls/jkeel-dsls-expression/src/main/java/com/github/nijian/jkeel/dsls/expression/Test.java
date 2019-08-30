package com.github.nijian.jkeel.dsls.expression;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Test {

  public static void main(String[] args) throws Exception {

    ANTLRInputStream input = new ANTLRInputStream("x+1");
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    ParseTree tree = parser.inject();

    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    classWriter.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "HelloWorld", null, "java/lang/Object",
        new String[] { "com/github/nijian/jkeel/dsls/expression/Expression" });
    // classWriter.visitSource("HelloWorld.java", null);

    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    methodVisitor.visitInsn(Opcodes.RETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute", "([Ljava/lang/String;)V", null, null);
    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

    //methodVisitor.visitLdcInsn("Hello world!");
    walker.walk(new ExpressionInjection(methodVisitor), tree);
    
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
        false);
    methodVisitor.visitInsn(Opcodes.RETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    DynamicClassLoader cl = new DynamicClassLoader();
    Class<?> c = cl.defineClass("HelloWorld", classWriter.toByteArray());
    Expression expr = (Expression) c.newInstance();
    expr.execute(null);

  }

}