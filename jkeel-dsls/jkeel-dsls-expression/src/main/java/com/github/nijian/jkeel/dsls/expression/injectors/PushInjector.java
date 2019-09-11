package com.github.nijian.jkeel.dsls.expression.injectors;

import java.math.BigDecimal;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PushInjector implements Injector {

  // receiver
  private MethodVisitor methodVisitor;

  private Class<?> operandType;

  private String value;

  public PushInjector(MethodVisitor methodVisitor, Class<?> operandType, String value) {
    this.methodVisitor = methodVisitor;
    this.operandType = operandType;
    this.value = value;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    if (operandType.isAssignableFrom(Integer.class)) {
      methodVisitor.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(value));
    } else if (operandType.isAssignableFrom(Long.class)) {

    } else if (operandType.isAssignableFrom(Float.class)) {

    } else if (operandType.isAssignableFrom(Double.class)) {

    } else if (operandType.isAssignableFrom(BigDecimal.class)) {

    } else {
      throw new RuntimeException("xxx");
    }

  }

}