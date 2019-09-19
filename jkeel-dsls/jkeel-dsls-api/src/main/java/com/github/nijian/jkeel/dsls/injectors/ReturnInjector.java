package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ReturnInjector extends Injector {

  @Override
  public void execute(Context ctx, InjectorExecutor executor) {
    MethodVisitor mv = ctx.getMethodVisitor();
    mv.visitInsn(Opcodes.ARETURN);
    mv.visitMaxs(0, 0);
    mv.visitEnd();
  }
  
}
