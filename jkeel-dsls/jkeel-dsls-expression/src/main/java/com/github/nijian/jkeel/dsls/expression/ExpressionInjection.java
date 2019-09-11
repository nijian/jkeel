package com.github.nijian.jkeel.dsls.expression;

import java.math.BigDecimal;
import java.util.Stack;

import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.AddInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.FackInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.MulInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.PushInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.SubInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadInjector;

import org.objectweb.asm.MethodVisitor;

public class ExpressionInjection extends ExpressionBaseListener {

  private InjectorExecutor injectorExecutor;

  private MethodVisitor methodVisitor;

  private Class<?> operandType;

  private Stack<Byte> opStack = new Stack<>();

  public ExpressionInjection(InjectorExecutor injectorExecutor, MethodVisitor methodVisitor) {
    this(injectorExecutor, methodVisitor, BigDecimal.class);
  }

  public ExpressionInjection(InjectorExecutor injectorExecutor, MethodVisitor methodVisitor, Class<?> operandType) {
    this.injectorExecutor = injectorExecutor;
    this.methodVisitor = methodVisitor;
    this.operandType = operandType;
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() == null && opStack.size() > 0) {
      byte op = opStack.pop();
      switch (op) {
      case Const.PLUS:
        injectorExecutor.execute(new AddInjector(methodVisitor, operandType));
        break;
      case Const.MINUS:
        injectorExecutor.execute(new SubInjector(methodVisitor, operandType));
        break;
      case Const.TIMES:
        injectorExecutor.execute(new MulInjector(methodVisitor, operandType));
        break;
      default:
        throw new RuntimeException("Found not support operator : " + op);
      }
    }
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    injectorExecutor.execute(new LoadInjector(methodVisitor, Object.class, 0, 1));// expression instance & context
    injectorExecutor.execute(new LoadInjector(methodVisitor, TermXPath.getXPath(ctx.VARIABLE().getText())));
    injectorExecutor.execute(new FackInjector(methodVisitor));

    // methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Const.EXP, "getValue",
    // "(Lorg/apache/commons/jxpath/JXPathContext;Ljava/lang/String;)Ljava/lang/Object;",
    // false);
    // // methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");//
    // check

    // methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object",
    // "toString", "()Ljava/lang/String;", false);
    // methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer",
    // "parseInt", "(Ljava/lang/String;)I",
    // false);
  }

  @Override
  public void enterScientific(ExpressionParser.ScientificContext ctx) {
    injectorExecutor.execute(new PushInjector(methodVisitor, operandType, ctx.getText()));// number in string format
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