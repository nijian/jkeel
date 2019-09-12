package com.github.nijian.jkeel.dsls.expression;

public interface Const {

  String EXP_SIGNATURE_TEMPLATE = "Lcom/github/nijian/jkeel/dsls/expression/Expression<%s>;";

  String EXECUTE_SIGNATURE_TEMPLATE = "(Lorg/apache/commons/jxpath/JXPathContext;)%s";

  //operator

  byte PLUS = 1;

  byte MINUS = 2;

  byte TIMES = 3;

  byte DIV = 4;

}