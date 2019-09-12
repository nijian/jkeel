package com.github.nijian.jkeel.dsls.expression;

import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.jxpath.JXPathContext;
import org.junit.Test;

public class ExpressionGrammarTest {

  @Test
  public void test() {
  
    try {
      // BigDecimal a = new BigDecimal("1");
      // BigDecimal b = new BigDecimal(2l);
      // a.add(b);
      Bean bean = new Bean();
      bean.setX(100);
      bean.setY(200);
      JXPathContext context = JXPathContext.newContext(bean);

      ExpressionMeta meta = new ExpressionMeta("HelloWorld", BigDecimal.class);
      InputStream dsl = ExpressionGrammarTest.class.getResourceAsStream("/aa.jexpr");
      byte[] clzB = ExpressionGenerator.generateClass(meta, dsl);

      DynamicClassLoader cl = new DynamicClassLoader();
      Class<?> c = cl.defineClass("HelloWorld", clzB);
      Expression<?> expr = (Expression<?>) c.newInstance();
      System.out.println(expr.execute(context));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}