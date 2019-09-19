package com.github.nijian.jkeel.dsls.expression;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;

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
      MathContext mc = new MathContext(5);
      meta.setInternalMathContext(mc);

      InputStream dsl = ExpressionGrammarTest.class.getResourceAsStream("/aa.jexpr");
      byte[] clzB = (new ExpressionGenerator()).generateClass(meta, dsl);

      DynamicClassLoader cl = new DynamicClassLoader();
      Class<?> c = cl.defineClass("HelloWorld", clzB);
      Expression<?> expr = (Expression<?>) c.newInstance();
      System.out.println(expr.execute(context, meta));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}