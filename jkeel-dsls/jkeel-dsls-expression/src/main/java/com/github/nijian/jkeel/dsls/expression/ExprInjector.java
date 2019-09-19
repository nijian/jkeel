package com.github.nijian.jkeel.dsls.expression;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.ClassInfoAware;
import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.AddInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.MulInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.SubInjector;

public class ExprInjector extends Injector implements ClassInfoAware, ExprClassInfoAware {

  public ExprInjector(Context<?> ctx, InjectorExecutor executor) {
    this.ctx = ctx;
    this.executor = executor;
  }

  public void ADD() {
    executor.execute(new AddInjector());
  }

  public void SUB() {
    executor.execute(new SubInjector());
  }

  public void MUL() {
    executor.execute(new MulInjector());
  }

  public void Scientific(ExpressionParser.ScientificContext sctx) {
    CAST(Cast.STRING_BIGDECIMAL, sctx.getText());
  }

  public void Variable(ExpressionParser.VariableContext sctx, int localVarIndex) {
    LOAD(0, 1);
    LDC(TermXPath.getXPath(sctx.VARIABLE().getText()));
    INVOKE(EXPR_INTERNAL_NAME, "getValue", OBJECT_SIGNATURE, JXPATHCONTEXT_SIGNATURE, STRING_SIGNATURE);
    CAST(Cast.OBJECT_STRING);
    VAR(localVarIndex);
    CAST(Cast.STRING_BIGDECIMAL, localVarIndex);
  }

  @Override
  protected void execute(Context<?> ctx, InjectorExecutor executor) {
  }
}