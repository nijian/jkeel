package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExprClassInfoAware;
import com.github.nijian.jkeel.dsls.expression.ExpressionInjection;
import com.github.nijian.jkeel.dsls.expression.ExpressionMeta;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.bcel.classfile.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteMethodInjector extends Injector implements ExprClassInfoAware {

  private static Logger logger = LoggerFactory.getLogger(ExecuteMethodInjector.class);

  private ParseTree tree;
  private ParseTreeWalker walker;
  private ExpressionMeta meta;

  public ExecuteMethodInjector(ParseTree tree, ParseTreeWalker walker, ExpressionMeta meta) {
    this.tree = tree;
    this.walker = walker;
    this.meta = meta;
  }

  @Override
  public void execute(Context<?> ctx, InjectorExecutor executor) {

    Class<?> retType = meta.getRetType();
    String retTypeSignature = Utility.getSignature(retType.getName());

    // execute method
    PUBLIC_METHOD("execute", null, retTypeSignature, JXPATHCONTEXT_SIGNATURE, EM_SIGNATURE);
    walker.walk(new ExpressionInjection(ctx, executor), tree);
    RETURN();

    // super execute method
    PUBLIC_METHOD("execute", null, OBJECT_SIGNATURE, JXPATHCONTEXT_SIGNATURE, EM_SIGNATURE);
    LOAD(0, 1, 2);
    INVOKE(meta.getName(), "execute", retTypeSignature, JXPATHCONTEXT_SIGNATURE, EM_SIGNATURE);
    RETURN();

    logger.info("Execute methods has been injected");
  }
}