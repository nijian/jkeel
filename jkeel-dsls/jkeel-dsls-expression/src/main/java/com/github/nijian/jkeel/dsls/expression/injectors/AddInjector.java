package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExprClassInfoAware;
import com.github.nijian.jkeel.dsls.injectors.LoadLocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInvokeInjector;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AddInjector implements Injector, ExprClassInfoAware {

  private final static Logger logger = LoggerFactory.getLogger(AddInjector.class);

  private final MethodVisitor mv;

  public AddInjector(MethodVisitor mv) {
    this.mv = mv;
  }

  /**
   * Always use BigDecimal to handle arithmetic operation
   */
  @Override
  public void execute(InjectorExecutor executor) {

    executor.execute(new LoadLocalVarInjector(mv, 2));

    // TODO
    mv.visitFieldInsn(Opcodes.GETFIELD, EM_INTERNAL_NAME, "internalMathContext", MC_SIGNATURE);

    executor.execute(new MethodInvokeInjector(mv, Opcodes.INVOKEVIRTUAL, BIGDECIMAL_INTERNAL_NAME, "add", false,
        BIGDECIMAL_SIGNATURE, BIGDECIMAL_SIGNATURE, MC_SIGNATURE));

    logger.info("Injected BigDecimal add operation");
  }

}