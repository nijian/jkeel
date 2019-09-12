package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalVarInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(CastInjector.class);

  private MethodVisitor methodVisitor;
  private int index;

  public LocalVarInjector(MethodVisitor methodVisitor, int index) {
    this.methodVisitor = methodVisitor;
    this.index = index;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    logger.info("create local variable with index : {} ", index);
    methodVisitor.visitVarInsn(Opcodes.ASTORE, index);
  }

}