package com.github.nijian.jkeel.dsls.expression;

import com.github.nijian.jkeel.dsls.Meta;

public class ExpressionMeta extends Meta {

  private Class<?> retType;

  private int scale;

  public ExpressionMeta(String name, Class<?> retType) {
    super.setName(name);
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