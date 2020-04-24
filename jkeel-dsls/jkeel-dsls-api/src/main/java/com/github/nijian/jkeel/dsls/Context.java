package com.github.nijian.jkeel.dsls;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

 public final class Context<T extends Meta> {

  private T meta;

  private ClassWriter cw;

  private MethodVisitor mv;

  public Context(T meta) {
  }

  public Context(T meta, MethodVisitor mv) {
    this(meta, null, mv);
  }

  public Context(T meta, ClassWriter cw, MethodVisitor mv) {
    this.meta = meta;
    this.cw = cw;
    this.mv = mv;
  }

  public T getMeta() {
    return meta;
  }

  public void setMeta(T meta) {
    this.meta = meta;
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