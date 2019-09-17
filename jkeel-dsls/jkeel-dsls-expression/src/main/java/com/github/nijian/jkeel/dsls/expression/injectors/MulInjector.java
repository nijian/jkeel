package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExprClassInfoAware;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulInjector implements Injector, ExprClassInfoAware {

  private static Logger logger = LoggerFactory.getLogger(MulInjector.class);

  // receiver
  private MethodVisitor mv;

  public MulInjector(MethodVisitor mv) {
    this.mv = mv;
  }

  /**
   * Always use BigDecimal to handle arithmetic operation
   */
  @Override
  public void execute(InjectorExecutor executor) {
    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, BIGDECIMAL_INTERNAL_NAME, "multiply",
        "(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", false);
    logger.info("Injected BigDecimal multiply operation");

  }

}