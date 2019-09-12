package com.github.nijian.jkeel.dsls.expression;

public class ExpressionMeta {

  private Class<?> retType;

  private int scale;

  public ExpressionMeta(Class<?> retType) {
    this.retType = retType;
  }

  public Class<?> getRetType() {
    return retType;
  }

  public void setRetType(Class<?> retType) {
    this.retType = retType;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

}