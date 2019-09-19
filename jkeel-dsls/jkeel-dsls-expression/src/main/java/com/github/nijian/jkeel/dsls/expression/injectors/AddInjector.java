package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExprClassInfoAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AddInjector extends Injector implements ExprClassInfoAware {

  private final static Logger logger = LoggerFactory.getLogger(AddInjector.class);

  /**
   * Always use BigDecimal to handle arithmetic operation
   */
  @Override
  public void execute(Context<?> ctx, InjectorExecutor executor) {

    LOAD(2);

    GETFIELD(EM_INTERNAL_NAME, "internalMathContext", MC_SIGNATURE);

    INVOKE(BIGDECIMAL_INTERNAL_NAME, "add", BIGDECIMAL_SIGNATURE, BIGDECIMAL_SIGNATURE, MC_SIGNATURE);

    logger.info("Injected BigDecimal add operation");
  }

}