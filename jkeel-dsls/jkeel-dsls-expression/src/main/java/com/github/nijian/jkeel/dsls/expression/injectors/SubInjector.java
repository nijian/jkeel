package com.github.nijian.jkeel.dsls.expression.injectors;

import java.math.BigDecimal;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class SubInjector implements Injector {

  // receiver
  private MethodVisitor methodVisitor;

  private Class<?> operandType;

  public SubInjector(MethodVisitor methodVisitor, Class<?> operandType) {
    this.methodVisitor = methodVisitor;
    this.operandType = operandType;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    if (operandType.isAssignableFrom(Integer.class)) {
      methodVisitor.visitInsn(Opcodes.ISUB);
    } else if (operandType.isAssignableFrom(Long.class)) {

    } else if (operandType.isAssignableFrom(Float.class)) {

    } else if (operandType.isAssignableFrom(Double.class)) {

    } else if (operandType.isAssignableFrom(BigDecimal.class)) {

    } else {
      throw new RuntimeException("xxx");
    }

  }

}