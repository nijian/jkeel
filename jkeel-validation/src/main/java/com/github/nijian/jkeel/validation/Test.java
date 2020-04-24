package com.github.nijian.jkeel.validation;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Test {

  public static void main(String[] args) throws Exception {

    ANTLRInputStream input = new ANTLRInputStream(System.in);
    ArrayInitLexer lexer = new ArrayInitLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ArrayInitParser parser = new ArrayInitParser(tokens);
    ParseTree tree = parser.init();

    long start = System.currentTimeMillis();
    for (int i = 0; i < 10000; i++) {
      ParseTreeWalker walker = new ParseTreeWalker();
      walker.walk(new ShortToUnicodeString(), tree);
      // System.out.println();
    }
    System.out.println(System.currentTimeMillis()-start);
  }

}