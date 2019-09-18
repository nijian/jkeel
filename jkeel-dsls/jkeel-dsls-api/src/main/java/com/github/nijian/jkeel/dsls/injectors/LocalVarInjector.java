package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalVarInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(CastInjector.class);

  private MethodVisitor mv;
  private int index;

  public LocalVarInjector(MethodVisitor mv, int index) {
    this.mv = mv;
    this.index = index;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    logger.info("create local variable with index : {} ", index);
    mv.visitVarInsn(Opcodes.ASTORE, index);
  }

}