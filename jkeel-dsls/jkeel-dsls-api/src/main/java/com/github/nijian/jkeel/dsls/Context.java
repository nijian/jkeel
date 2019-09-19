package com.github.nijian.jkeel.dsls;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public final class Context {

  private ClassWriter cw;

  private MethodVisitor mv;

  public Context() {
  }

  public Context(MethodVisitor mv) {
    this(null, mv);
  }

  public Context(ClassWriter cw, MethodVisitor mv) {
    this.cw = cw;
    this.mv = mv;
  }

  public ClassWriter getClassWriter() {
    return cw;
  }

  public void setClassWriter(ClassWriter cw) {
    this.cw = cw;
  }

  public MethodVisitor getMethodVisitor() {
    return mv;
  }

  public void setMethodVisitor(MethodVisitor mv) {
    this.mv = mv;
  }

}