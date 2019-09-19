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
   * 
   * @param <V>        value type
   * @param jxpContext jx path context
   * @param xpath      xpath
   * @return real value
   */
  @SuppressWarnings("unchecked")
  protected <V> V getValue(JXPathContext jxpContext, String xpath) {
    return (V) jxpContext.getValue(xpath);
  }

}