package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalVarInjector extends Injector {

  private static Logger logger = LoggerFactory.getLogger(CastInjector.class);

  private int index;

  public LocalVarInjector(int index) {
    this.index = index;
  }

  @Override
  public void execute(Context ctx, InjectorExecutor executor) {
    logger.info("create local variable with index : {} ", index);
    ctx.getMethodVisitor().visitVarInsn(Opcodes.ASTORE, index);
  }

}