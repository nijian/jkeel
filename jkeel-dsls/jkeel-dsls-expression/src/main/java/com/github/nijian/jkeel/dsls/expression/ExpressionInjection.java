package com.github.nijian.jkeel.dsls.expression;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExpressionInjection extends ExpressionBaseListener {

  private MethodVisitor methodVisitor;

  public ExpressionInjection(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() != null || (ctx.LPAREN() != null && ctx.RPAREN() != null)) {
      System.out.println("ignore :" + ctx.getText());
      return;
    }
    System.out.println("add :" + ctx.getText());
    methodVisitor.visitInsn(Opcodes.IADD);
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1); // context
    methodVisitor.visitLdcInsn(TermXPath.getXPath(ctx.VARIABLE().getText()));
    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, Const.EXP, "getValue",
        "(Lorg/apache/commons/jxpath/JXPathContext;Ljava/lang/String;)I", false);
  }

  @Override
  public void enterScientific(ExpressionParser.ScientificContext ctx) {
    System.out.println("enterScien:" + ctx.getText());
    methodVisitor.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(ctx.getText()));
  }

}