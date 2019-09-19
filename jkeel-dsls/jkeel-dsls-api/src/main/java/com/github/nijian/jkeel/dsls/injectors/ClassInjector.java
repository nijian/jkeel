package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.ClassWriter;

public final class ClassInjector extends Injector {

  private final int version;

  private final int access;

  private final String name;

  private final String signature;

  private final String superName;

  private final String[] interfaces;

  public ClassInjector(int version, int access, String name, String signature, String superName, String[] interfaces) {
    this.version = version;
    this.access = access;
    this.name = name;
    this.signature = signature;
    this.superName = superName;
    this.interfaces = interfaces;
  }

  @Override
  public void execute(final Context<?> ctx, final InjectorExecutor executor) {
    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    ctx.setClassWriter(cw);
    cw.visit(version, access, name, signature, superName, interfaces);
  }

}