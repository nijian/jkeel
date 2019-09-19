package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

/**
 * Load reference object from constant pool, then push it on the top of stack.
 * 
 */
public class LoadConstInjector extends Injector {

  private Object data;

  public LoadConstInjector(Object data) {
    this.data = data;
  }

  @Override
  public void execute(Context ctx, InjectorExecutor executor) {
    ctx.getMethodVisitor().visitLdcInsn(data);
  }

}