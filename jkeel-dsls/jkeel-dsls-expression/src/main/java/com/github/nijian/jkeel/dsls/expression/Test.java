package com.github.nijian.jkeel.dsls.expression;

import org.apache.commons.jxpath.JXPathContext;

public class Test {

  public static void main(String[] args) throws Exception {
    int x = 2;
    int b = x+(4-x);
    Bean bean = new Bean();
    bean.setX(100);
    bean.setY(200);
    JXPathContext context = JXPathContext.newContext(bean);

    byte[] clzB = ExpressionGenerator.generate("HelloWorld", "c+(1+b)+(100)");

    DynamicClassLoader cl = new DynamicClassLoader();
    Class<?> c = cl.defineClass("HelloWorld", clzB);
    Expression expr = (Expression) c.newInstance();
    System.out.println(expr.execute(context));

  }

}