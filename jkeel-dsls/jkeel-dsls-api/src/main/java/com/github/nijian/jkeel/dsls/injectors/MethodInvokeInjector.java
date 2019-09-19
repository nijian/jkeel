package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodInvokeInjector extends Injector {

  private static Logger logger = LoggerFactory.getLogger(MethodInvokeInjector.class);

  private int op;
  private String owner;
  private String name;
  private boolean isInterface;
  private String retTypeSignature;
  private String[] argTypeSignatures;

  public MethodInvokeInjector(int op, String owner, String name, boolean isInterface, String retTypeSignature,
      String... argTypeSignatures) {
    this.op = op;
    this.owner = owner;
    this.name = name;
    this.isInterface = isInterface;
    this.retTypeSignature = retTypeSignature;
    this.argTypeSignatures = argTypeSignatures;
  }

  @Override
  public void execute(Context ctx, InjectorExecutor executor) {
    logger.info("Injected method : {}", "(" + String.join("", argTypeSignatures) + ")" + retTypeSignature);
    ctx.getMethodVisitor().visitMethodInsn(op, owner, name,
        "(" + String.join("", argTypeSignatures) + ")" + retTypeSignature, isInterface);
  }

}
