package com.github.nijian.jkeel.dsls.expression;

public class ExpressionMeta {

  private Class<?> operandType;

  private int scale;

  public Class<?> getOperandType() {
    return operandType;
  }

  public void setOperandType(Class<?> operandType) {
    this.operandType = operandType;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

}