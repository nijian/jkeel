package com.github.nijian.jkeel.dsls.expression;

import java.math.MathContext;
import java.math.RoundingMode;

import com.github.nijian.jkeel.dsls.Meta;

public class ExpressionMeta extends Meta {

  private Class<?> retType;

  public MathContext internalMathContext = MathContext.DECIMAL64;

  private int scale = 6;

  private RoundingMode roundingMode = RoundingMode.HALF_UP;

  public ExpressionMeta() {
  }

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

  public MathContext getInternalMathContext() {
    return internalMathContext;
  }

  public void setInternalMathContext(MathContext internalMathContext) {
    this.internalMathContext = internalMathContext;
  }

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public RoundingMode getRoundingMode() {
    return roundingMode;
  }

  public void setRoundingMode(RoundingMode roundingMode) {
    this.roundingMode = roundingMode;
  }

}