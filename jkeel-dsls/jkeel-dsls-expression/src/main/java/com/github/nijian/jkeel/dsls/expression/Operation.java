package com.github.nijian.jkeel.dsls.expression;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.ClassInfoAware;
import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.AddInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.MulInjector;
import com.github.nijian.jkeel.dsls.expression.injectors.SubInjector;
import com.github.nijian.jkeel.dsls.injectors.CastInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadConstInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadLocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.LocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInvokeInjector;

import org.objectweb.asm.Opcodes;

public class Operation implements ClassInfoAware, ExprClassInfoAware {

  private final Context ctx;

  private final InjectorExecutor executor;

  public Operation(Context ctx, InjectorExecutor executor) {
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
    executor.execute(new CastInjector(Cast.STRING_BIGDECIMAL, /* number in string format */sctx.getText()));
  }

  public void Variable(ExpressionParser.VariableContext sctx, int localVarIndex) {
    executor.execute(new LoadLocalVarInjector(0, 1));// expression instance & context
    executor.execute(new LoadConstInjector(TermXPath.getXPath(sctx.VARIABLE().getText())));
    executor.execute(new MethodInvokeInjector(Opcodes.INVOKEVIRTUAL, EXPR_INTERNAL_NAME, "getValue", false,
        OBJECT_SIGNATURE, JXPATHCONTEXT_SIGNATURE, STRING_SIGNATURE));
    executor.execute(new CastInjector(Cast.OBJECT_STRING));
    executor.execute(new LocalVarInjector(localVarIndex));
    executor.execute(new CastInjector(Cast.STRING_BIGDECIMAL, localVarIndex));
  }
}