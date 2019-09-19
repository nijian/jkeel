package com.github.nijian.jkeel.dsls.expression;

import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.github.nijian.jkeel.dsls.ClassInfoAware;
import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.ExecFunc;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionInjection extends ExpressionBaseListener implements ClassInfoAware, ExprClassInfoAware {

  private static Logger logger = LoggerFactory.getLogger(ExpressionInjection.class);

  private final Context ctx;

  private final InjectorExecutor executor;

  // maybe find xpath by meta info
  private final ExpressionMeta meta;

  private Stack<Byte> opStack = new Stack<>();

  /**
   * ignore three input variables
   */
  private int localVarIndex = 3;

  private final Operation OP;

  public ExpressionInjection(Context ctx, InjectorExecutor executor) {
    this(ctx, executor, new ExpressionMeta());
  }

  public ExpressionInjection(Context ctx, InjectorExecutor executor, ExpressionMeta meta) {
    this.ctx = ctx;
    this.executor = executor;
    this.meta = meta;
    OP = new Operation(ctx, executor);
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() == null && opStack.size() > 0) {
      byte op = opStack.pop();
      switch (op) {
      case Const.PLUS:
        exec(OP::ADD);
        break;
      case Const.MINUS:
        exec(OP::SUB);
        break;
      case Const.TIMES:
        exec(OP::MUL);
        break;
      default:
        logger.error("Unsupported operator : {}", op);
        throw new RuntimeException("Unsupported operator : " + op);
      }
    }
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    exec(OP::Variable, ctx, localVarIndex);
    localVarIndex++;
  }

  @Override
  public void enterScientific(ExpressionParser.ScientificContext ctx) {
    exec(OP::Scientific, ctx);
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

  private void exec(ExecFunc c) {
    c.exec();
  }

  private void exec(Consumer<ExpressionParser.ScientificContext> s, ExpressionParser.ScientificContext c) {
    s.accept(c);
  }

  private void exec(BiConsumer<ExpressionParser.VariableContext, Integer> s, ExpressionParser.VariableContext c,
      int localVarIndex) {
    s.accept(c, localVarIndex);
  }

}