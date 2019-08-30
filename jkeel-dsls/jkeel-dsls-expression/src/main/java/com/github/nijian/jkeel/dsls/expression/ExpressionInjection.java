package com.github.nijian.jkeel.dsls.expression;

import org.objectweb.asm.MethodVisitor;

public class ExpressionInjection extends ExpressionBaseListener {

  private MethodVisitor methodVisitor;
  
  public ExpressionInjection(MethodVisitor methodVisitor){
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    //System.out.print(ctx.VARIABLE().getText());
    methodVisitor.visitLdcInsn("Hello variable : " + ctx.VARIABLE().getText());
  }
}