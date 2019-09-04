package com.github.nijian.jkeel.dsls.expression;

import java.util.Stack;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExpressionInjection extends ExpressionBaseListener {

  private MethodVisitor methodVisitor;

  private Stack<Byte> opStack = new Stack<>();

  public ExpressionInjection(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() == null && opStack.size() > 0) {
      byte op = opStack.pop();
      switch (op) {
      case Const.PLUS:
        methodVisitor.visitInsn(Opcodes.IADD);
        break;
      case Const.MINUS:
        methodVisitor.visitInsn(Opcodes.ISUB);
        break;
      case Const.TIMES:
        methodVisitor.visitInsn(Opcodes.IMUL);
        break;
      default:
        throw new RuntimeException("Found not support operator : " + op);
      }
    }
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
    methodVisitor.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(ctx.getText()));
  }

  @Override
  public void enterPlus(ExpressionParser.PlusContext ctx) {
    opStack.push(Const.PLUS);
  }

  @Override
  public void exitMinus(ExpressionParser.MinusContext ctx) {
    opStack.push(Const.MINUS);
  }

  @Override
  public void enterTimes(ExpressionParser.TimesContext ctx) {
    opStack.push(Const.TIMES);
  }
}