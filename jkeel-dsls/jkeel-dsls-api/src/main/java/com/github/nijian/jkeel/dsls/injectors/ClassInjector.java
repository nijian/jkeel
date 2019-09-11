package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.ClassWriter;

public class ClassInjector implements Injector {

  private ClassWriter classWriter;

  private int version;

  private int access;

  private String name;

  private String signature;

  private String superName;

  private String[] interfaces;

  public ClassInjector(ClassWriter classWriter, int version, int access, String name, String signature,
      String superName, String[] interfaces) {
    this.classWriter = classWriter;
    this.version = version;
    this.access = access;
    this.name = name;
    this.signature = signature;
    this.superName = superName;
    this.interfaces = interfaces;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    classWriter.visit(version, access, name, signature, superName, interfaces);
  }

}