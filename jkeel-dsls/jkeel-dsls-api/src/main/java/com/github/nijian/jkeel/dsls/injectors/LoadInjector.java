package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LoadInjector implements Injector {

  // receiver
  private MethodVisitor methodVisitor;

  private Class<?> dataType;

  private int[] indexes;

  // for constant

  private boolean isLDC = false;

  private Object data;

  public LoadInjector(MethodVisitor methodVisitor, Class<?> dataType, int... indexes) {
    this.methodVisitor = methodVisitor;
    this.dataType = dataType;
    this.indexes = indexes;
  }

  public LoadInjector(MethodVisitor methodVisitor, Object data) {
    this.methodVisitor = methodVisitor;
    this.data = data;
    isLDC = true;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    if (isLDC) {
      methodVisitor.visitLdcInsn(data);
    } else {
      if (dataType.isAssignableFrom(Object.class)) {
        for (int i = 0; i < indexes.length; i++) {
          methodVisitor.visitVarInsn(Opcodes.ALOAD, indexes[i]);
        }
      } else {
        throw new RuntimeException("xxx");
      }
    }

  }

}