package com.github.nijian.jkeel.dsls.expression;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.jxpath.JXPathContext;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Test {

  public static void main(String[] args) throws Exception {

    Bean bean = new Bean();
    bean.setX(100);
    bean.setY(500);
    JXPathContext context = JXPathContext.newContext(bean);

    ANTLRInputStream input = new ANTLRInputStream("d");
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    ParseTree tree = parser.inject();

    ParseTreeWalker walker = new ParseTreeWalker();

    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    classWriter.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "HelloWorld", null,
        "com/github/nijian/jkeel/dsls/expression/Expression", null);

    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/github/nijian/jkeel/dsls/expression/Expression", "<init>",
        "()V", false);
    methodVisitor.visitInsn(Opcodes.RETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)I", null, null);

    walker.walk(new ExpressionInjection(methodVisitor), tree);

    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    DynamicClassLoader cl = new DynamicClassLoader();
    Class<?> c = cl.defineClass("HelloWorld", classWriter.toByteArray());
    Expression expr = (Expression) c.newInstance();
    System.out.println(expr.execute(context));

  }

}