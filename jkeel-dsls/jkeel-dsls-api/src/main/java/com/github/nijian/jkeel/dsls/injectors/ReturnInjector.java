package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ReturnInjector implements Injector {

  private MethodVisitor mv;

  public ReturnInjector(MethodVisitor mv) {
    this.mv = mv;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    mv.visitInsn(Opcodes.ARETURN);
    mv.visitMaxs(0, 0);
    mv.visitEnd();
  }
}
