package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;

/**
 * Load reference object from constant pool, then push it on the top of stack.
 * 
 */
public class LoadConstInjector implements Injector {

  // receiver
  private MethodVisitor mv;

  private Object data;

  public LoadConstInjector(MethodVisitor mv, Object data) {
    this.mv = mv;
    this.data = data;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    mv.visitLdcInsn(data);
  }

}