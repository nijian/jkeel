package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodInvokeInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(MethodInvokeInjector.class);

  private MethodVisitor methodVisitor;

  private int op;
  private String owner;
  private String name;
  private boolean isInterface;
  private String retTypeSignature;
  private String[] argTypeSignatures;

  public MethodInvokeInjector(MethodVisitor methodVisitor, int op, String owner, String name, boolean isInterface,
      String retTypeSignature, String... argTypeSignatures) {
    this.methodVisitor = methodVisitor;
    this.op = op;
    this.owner = owner;
    this.name = name;
    this.isInterface = isInterface;
    this.retTypeSignature = retTypeSignature;
    this.argTypeSignatures = argTypeSignatures;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    logger.info("Injected method : {}", "(" + String.join("", argTypeSignatures) + ")" + retTypeSignature);
    methodVisitor.visitMethodInsn(op, owner, name, "(" + String.join("", argTypeSignatures) + ")" + retTypeSignature,
        isInterface);
  }
}
