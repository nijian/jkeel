package com.github.nijian.jkeel.dsls.expression;

import org.apache.commons.jxpath.JXPathContext;

public abstract class Expression {

  protected abstract int execute(JXPathContext jxpContext);

  protected static int getValue(JXPathContext jxpContext, String xpath) {
    return (int) jxpContext.getValue(xpath);
  }

}