package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(AddInjector.class);

  // receiver
  private MethodVisitor methodVisitor;

  public AddInjector(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  /**
   * Always use BigDecimal to handle arithmetic operation
   */
  @Override
  public void execute(InjectorExecutor executor) {
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, BIGDECIMAL_INTERNAL_NAME, "add",
        "(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", false);
    logger.info("Injected BigDecimal add operation");
  }

}