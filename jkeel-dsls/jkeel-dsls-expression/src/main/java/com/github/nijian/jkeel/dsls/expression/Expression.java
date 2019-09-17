package com.github.nijian.jkeel.dsls.expression;

import org.apache.commons.jxpath.JXPathContext;

/**
 * Expression abstract
 * 
 * @param <T> type of final result
 */
public abstract class Expression<T> {

  protected abstract T execute(JXPathContext jxpContext, ExpressionMeta meta);

  /**
   * Get real value by xpath at runtime
   */
  @SuppressWarnings("unchecked")
  protected <V> V getValue(JXPathContext jxpContext, String xpath) {
    return (V) jxpContext.getValue(xpath);
  }

}