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

  private Stack<Byte> opStack = new Stack<>();

  /**
   * ignore pre three input variables
   */
  private int localVarIndex = 3;

  private final ExprInjector OP;

  public ExpressionInjection(Context<?> ctx, InjectorExecutor executor) {
    OP = new ExprInjector(ctx, executor);
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() == null && opStack.size() > 0) {
      byte op = opStack.pop();
      switch (op) {
      case PLUS:
        inject(OP::ADD);
        break;
      case MINUS:
        inject(OP::SUB);
        break;
      case TIMES:
        inject(OP::MUL);
        break;
      default:
        logger.error("Unsupported operator : {}", op);
        throw new RuntimeException("Unsupported operator : " + op);
      }
    }
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    inject(OP::Variable, ctx, localVarIndex);
    localVarIndex++;
  }

  @Override
  public void enterScientific(ExpressionParser.ScientificContext ctx) {
    inject(OP::Scientific, ctx);
  }

  @Override
  public void enterPlus(ExpressionParser.PlusContext ctx) {
    opStack.push(PLUS);
  }

  @Override
  public void exitMinus(ExpressionParser.MinusContext ctx) {
    opStack.push(MINUS);
  }

  @Override
  public void enterTimes(ExpressionParser.TimesContext ctx) {
    opStack.push(TIMES);
  }

  private void inject(ExecFunc c) {
    c.exec();
  }

  private void inject(Consumer<ExpressionParser.ScientificContext> s, ExpressionParser.ScientificContext c) {
    s.accept(c);
  }

  private void inject(BiConsumer<ExpressionParser.VariableContext, Integer> s, ExpressionParser.VariableContext c,
      int localVarIndex) {
    s.accept(c, localVarIndex);
  }

}