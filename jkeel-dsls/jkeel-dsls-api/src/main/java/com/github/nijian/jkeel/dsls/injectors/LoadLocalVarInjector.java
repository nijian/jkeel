package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Load reference object from constant pool or local variable table, then push
 * it on the top of stack.
 * 
 */
public class LoadLocalVarInjector extends Injector {

  private int[] indexes;

  public LoadLocalVarInjector(int... indexes) {
    this.indexes = indexes;
  }

  @Override
  public void execute(Context<?> ctx, InjectorExecutor executor) {
    MethodVisitor mv = ctx.getMethodVisitor();
    for (int i = 0; i < indexes.length; i++) {
      mv.visitVarInsn(Opcodes.ALOAD, indexes[i]);
    }
  }

}