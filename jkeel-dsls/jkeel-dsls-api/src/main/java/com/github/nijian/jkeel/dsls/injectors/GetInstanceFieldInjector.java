package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class GetInstanceFieldInjector extends Injector {

  private String owner;
  private String name;
  private String fieldSignature;

  public GetInstanceFieldInjector(String owner, String name, String fieldSignature) {
    this.owner = owner;
    this.name = name;
    this.fieldSignature = fieldSignature;
  }

  @Override
  public void execute(Context ctx, InjectorExecutor executor) {
    MethodVisitor mv = ctx.getMethodVisitor();
    mv.visitFieldInsn(Opcodes.GETFIELD, owner, name, fieldSignature);
  }

}