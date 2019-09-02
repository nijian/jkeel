package com.github.nijian.jkeel.dsls.expression;

public class TermXPath{

  public static String getXPath(String term){
    if(term.equals("c")){
      return "x";
    }

    return "y";
  }

}