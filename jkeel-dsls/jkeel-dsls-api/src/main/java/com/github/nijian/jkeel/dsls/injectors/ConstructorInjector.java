package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ConstructorInjector extends Injector {

  private String name;

  public ConstructorInjector(String name) {
    this.name = name;
  }

  @Override
  public void execute(Context<?> ctx, InjectorExecutor executor) {
    MethodVisitor mv = ctx.getClassWriter().visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    mv.visitVarInsn(Opcodes.ALOAD, 0);
    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, name, "<init>", "()V", false);
    mv.visitInsn(Opcodes.RETURN);
    mv.visitMaxs(0, 0);
    mv.visitEnd();
  }

}