package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExprClassInfoAware;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubInjector implements Injector, ExprClassInfoAware {

  private static Logger logger = LoggerFactory.getLogger(SubInjector.class);

  // receiver
  private MethodVisitor methodVisitor;

  public SubInjector(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  /**
   * Always use BigDecimal to handle arithmetic operation
   */
  @Override
  public void execute(InjectorExecutor executor) {
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, BIGDECIMAL_INTERNAL_NAME, "subtract",
        "(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", false);
    logger.info("Injected BigDecimal subtract operation");

  }

}