package com.github.nijian.jkeel.dsls.expression;

import java.io.IOException;
import java.io.InputStream;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.injectors.ExecuteMethodInjector;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExpressionGenerator extends Injector implements ExprClassInfoAware {

  private final static Logger logger = LoggerFactory.getLogger(ExpressionGenerator.class);

  public void inject(ExpressionMeta meta, MethodVisitor mv, InputStream dsl) {
    try {
      injectByInput(meta, mv, CharStreams.fromStream(dsl));
    } catch (IOException e) {
      logger.error("Fail to construct char stream by input stream", e);
      throw new RuntimeException(e);
    } finally {
      if (dsl != null) {
        try {
          dsl.close();
        } catch (Exception e) {
          logger.warn("Fail to close dsl input stream");
        }
      }
    }
  }

  public void inject(ExpressionMeta meta, MethodVisitor mv, String dsl) {
    injectByInput(meta, mv, CharStreams.fromString(dsl));
  }

  private void injectByInput(ExpressionMeta meta, MethodVisitor mv, CharStream input) {
    Context ctx = new Context(mv);
    InjectorExecutor executor = new InjectorExecutor(ctx);
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(new ExpressionInjection(ctx, executor, meta), tree);
  }

  public byte[] generateClass(ExpressionMeta meta, InputStream dsl) {
    try {
      return generateClassByInput(meta, CharStreams.fromStream(dsl));
    } catch (IOException e) {
      logger.error("Fail to construct char stream by input stream", e);
      throw new RuntimeException(e);
    } finally {
      if (dsl != null) {
        try {
          dsl.close();
        } catch (Exception e) {
          logger.warn("Fail to close dsl input stream");
        }
      }
    }
  }

  public byte[] generateClass(ExpressionMeta meta, String dsl) {
    return generateClassByInput(meta, CharStreams.fromString(dsl));
  }

  private byte[] generateClassByInput(ExpressionMeta meta, CharStream input) {

    Context ctx = new Context();
    setInjectorExecutor(new InjectorExecutor(ctx));

    // class
    String retTypeSignature = Utility.getSignature(meta.getRetType().getName());
    String exprSignature = String.format(EXP_SIGNATURE_TEMPLATE, retTypeSignature);
    CLASS(meta.getName(), exprSignature, EXPR_INTERNAL_NAME);

    // constructor
    CONSTRUCTOR(EXPR_INTERNAL_NAME);

    // execute method
    ParseTree tree = genTree(input);
    ParseTreeWalker walker = new ParseTreeWalker();
    EXECUTEMETHOD(tree, walker, meta);

    return ctx.getClassWriter().toByteArray();
  }

  private ParseTree genTree(CharStream input) {
    ExpressionLexer lexer = new ExpressionLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExpressionParser parser = new ExpressionParser(tokens);
    return parser.inject();
  }

  @Override
  public void execute(Context ctx, InjectorExecutor executor) {

  }

  private void EXECUTEMETHOD(ParseTree tree, ParseTreeWalker walker, ExpressionMeta meta) {
    executor.execute(new ExecuteMethodInjector(tree, walker, meta));
  }

}