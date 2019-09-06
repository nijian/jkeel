package com.github.nijian.jkeel.dsls.expression;

import org.apache.commons.jxpath.JXPathContext;

public abstract class Expression<T> {

  protected abstract T execute(JXPathContext jxpContext);

  @SuppressWarnings("unchecked")
  protected T getValue(JXPathContext jxpContext, String xpath) {
    return (T) jxpContext.getValue(xpath);
  }

}