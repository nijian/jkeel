package com.github.nijian.jkeel.dsls.expression;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExpressionInjection extends ExpressionBaseListener {

  private MethodVisitor methodVisitor;

  public ExpressionInjection(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {

    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1); // context
    methodVisitor.visitLdcInsn(TermXPath.getXPath(ctx.VARIABLE().getText()));
    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/github/nijian/jkeel/dsls/expression/Expression",
        "getValue", "(Lorg/apache/commons/jxpath/JXPathContext;Ljava/lang/String;)I", false);
    methodVisitor.visitInsn(Opcodes.IRETURN);
  }
}