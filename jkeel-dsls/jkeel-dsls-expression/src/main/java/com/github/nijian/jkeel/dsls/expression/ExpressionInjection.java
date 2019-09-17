package com.github.nijian.jkeel.dsls.expression;

import java.util.Stack;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.AddInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.GetValueInvokeInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.MulInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.SubInjector;
import com.github.nijian.jkeel.dsls.injectors.CastInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadInjector;
import com.github.nijian.jkeel.dsls.injectors.LocalVarInjector;

import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionInjection extends ExpressionBaseListener {

  private static Logger logger = LoggerFactory.getLogger(ExpressionInjection.class);

  private InjectorExecutor injectorExecutor;

  private MethodVisitor methodVisitor;

  private ExpressionMeta meta;

  private Stack<Byte> opStack = new Stack<>();

  /**
   * ignore three input variables
   */
  private int localVarIndex = 3;

  public ExpressionInjection(InjectorExecutor injectorExecutor, MethodVisitor methodVisitor) {
    this(injectorExecutor, methodVisitor, new ExpressionMeta());
  }

  public ExpressionInjection(InjectorExecutor injectorExecutor, MethodVisitor methodVisitor, ExpressionMeta meta) {
    this.injectorExecutor = injectorExecutor;
    this.methodVisitor = methodVisitor;
    this.meta = meta;
  }

  @Override
  public void exitExpression(ExpressionParser.ExpressionContext ctx) {
    if (ctx.atom() == null && opStack.size() > 0) {
      byte op = opStack.pop();
      switch (op) {
      case Const.PLUS:
        injectorExecutor.execute(new AddInjector(methodVisitor, meta));
        break;
      case Const.MINUS:
        injectorExecutor.execute(new SubInjector(methodVisitor));
        break;
      case Const.TIMES:
        injectorExecutor.execute(new MulInjector(methodVisitor));
        break;
      default:
        logger.error("Unsupported operator : {}", op);
        throw new RuntimeException("Unsupported operator : " + op);
      }
    }
  }

  @Override
  public void enterVariable(ExpressionParser.VariableContext ctx) {
    injectorExecutor.execute(new LoadInjector(methodVisitor, Object.class, 0, 1));// expression instance & context
    injectorExecutor.execute(new LoadInjector(methodVisitor, TermXPath.getXPath(ctx.VARIABLE().getText())));
    injectorExecutor.execute(new GetValueInvokeInjector(methodVisitor));
    injectorExecutor.execute(new CastInjector(methodVisitor, Cast.OBJECT_STRING));
    injectorExecutor.execute(new LocalVarInjector(methodVisitor, localVarIndex));
    injectorExecutor.execute(new CastInjector(methodVisitor, Cast.STRING_BIGDECIMAL, localVarIndex));
    localVarIndex++;
  }

  @Override
  public void enterScientific(ExpressionParser.ScientificContext ctx) {
    injectorExecutor
        .execute(new CastInjector(methodVisitor, Cast.STRING_BIGDECIMAL, /* number in string format */ctx.getText()));
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