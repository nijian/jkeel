package com.github.nijian.jkeel.dsls.expression.injectors;

import java.math.BigDecimal;

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

  private Class<?> operandType;

  public AddInjector(MethodVisitor methodVisitor, Class<?> operandType) {
    this.methodVisitor = methodVisitor;
    this.operandType = operandType;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    if (operandType.isAssignableFrom(Integer.class)) {
      methodVisitor.visitInsn(Opcodes.IADD);
    } else if (operandType.isAssignableFrom(Long.class)) {

    } else if (operandType.isAssignableFrom(Float.class)) {

    } else if (operandType.isAssignableFrom(Double.class)) {

    } else if (operandType.isAssignableFrom(BigDecimal.class)) {

    } else {
      logger.error("Unsupported operand type : {}", operandType);
      throw new RuntimeException("Unsupported operand type : " + operandType);
    }

  }

}