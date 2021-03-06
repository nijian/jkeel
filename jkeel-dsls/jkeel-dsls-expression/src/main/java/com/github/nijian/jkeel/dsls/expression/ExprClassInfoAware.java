package com.github.nijian.jkeel.dsls.expression;

import java.math.MathContext;

import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.Type;

public interface ExprClassInfoAware {

  byte PLUS = 1;

  byte MINUS = 2;

  byte TIMES = 3;

  byte DIV = 4;
  
  String EXPR_INTERNAL_NAME = Type.getInternalName(Expression.class);

  String MC_INTERNAL_NAME = Type.getInternalName(MathContext.class);

  String MC_SIGNATURE = Utility.getSignature(MathContext.class.getName());

  String EM_INTERNAL_NAME = Type.getInternalName(ExpressionMeta.class);

  String EM_SIGNATURE = Utility.getSignature(ExpressionMeta.class.getName());

  String EXP_SIGNATURE_TEMPLATE = "Lcom/github/nijian/jkeel/dsls/expression/Expression<%s>;";

}