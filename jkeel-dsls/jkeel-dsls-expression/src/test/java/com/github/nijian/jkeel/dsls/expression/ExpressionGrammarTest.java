package com.github.nijian.jkeel.dsls.expression;

import java.io.InputStream;

import org.apache.commons.jxpath.JXPathContext;
import org.junit.Test;

public class ExpressionGrammarTest {

  @Test
  public void test() {
 
    try {
      Bean bean = new Bean();
      bean.setX(100);
      bean.setY(200);
      JXPathContext context = JXPathContext.newContext(bean);

      InputStream dsl = ExpressionGrammarTest.class.getResourceAsStream("/aa.jexpr");
      byte[] clzB = ExpressionGenerator.generate("HelloWorld", dsl);

      DynamicClassLoader cl = new DynamicClassLoader();
      Class<?> c = cl.defineClass("HelloWorld", clzB);
      Expression expr = (Expression) c.newInstance();
      System.out.println(expr.execute(context));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}