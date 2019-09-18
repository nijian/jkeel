package com.github.nijian.jkeel.dsls.expression;

import java.util.Stack;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.ClassInfoAware;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.AddInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.MulInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.SubInjector;
import com.github.nijian.jkeel.dsls.injectors.CastInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadConstInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadLocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.LocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInvokeInjector;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionInjection extends ExpressionBaseListener implements ClassInfoAware, ExprClassInfoAware {

  private static Logger logger = LoggerFactory.getLogger(ExpressionInjection.class);

  private InjectorExecutor executor;

  private MethodVisitor mv;

  // maybe find xpath by meta info
  private ExpressionMeta meta;

  private Stack<Byte> opStack = new Stack<>();

  /**
   * ignore three input variables
   */
  private int localVarIndex = 3;

  public ExpressionInjection(InjectorExecutor executor, MethodVisitor mv) {
    this(executor, mv, new ExpressionMeta());
  }

  public ExpressionInjection(InjectorExecutor executor, MethodVisitor mv, ExpressionMeta meta) {
    this.executor = executor;
    this.mv = mv;
    this.meta = meta;
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() == null && opStack.size() > 0) {
      byte op = opStack.pop();
      switch (op) {
      case Const.PLUS:
        executor.execute(new AddInjector(mv));
        break;
      case Const.MINUS:
        executor.execute(new SubInjector(mv));
        break;
      case Const.TIMES:
        executor.execute(new MulInjector(mv));
        break;
      default:
        logger.error("Unsupported operator : {}", op);
        throw new RuntimeException("Unsupported operator : " + op);
      }
    }
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    executor.execute(new LoadLocalVarInjector(mv, 0, 1));// expression instance & context
    executor.execute(new LoadConstInjector(mv, TermXPath.getXPath(ctx.VARIABLE().getText())));
    executor.execute(new MethodInvokeInjector(mv, Opcodes.INVOKEVIRTUAL, EXPR_INTERNAL_NAME, "getValue", false,
        OBJECT_SIGNATURE, JXPATHCONTEXT_SIGNATURE, STRING_SIGNATURE));
    executor.execute(new CastInjector(mv, Cast.OBJECT_STRING));
    executor.execute(new LocalVarInjector(mv, localVarIndex));
    executor.execute(new CastInjector(mv, Cast.STRING_BIGDECIMAL, localVarIndex));
    localVarIndex++;
  }

  @Override
  public void enterScientific(ExpressionParser.ScientificContext ctx) {
    executor.execute(new CastInjector(mv, Cast.STRING_BIGDECIMAL, /* number in string format */ctx.getText()));
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