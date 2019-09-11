package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.Const;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class GetValueInvokeInjector implements Injector {

  // receiver
  private MethodVisitor methodVisitor;

  public GetValueInvokeInjector(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Const.EXP, "getValue",
        "(Lorg/apache/commons/jxpath/JXPathContext;Ljava/lang/String;)Ljava/lang/Object;", false);
  }

}