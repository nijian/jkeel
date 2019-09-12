package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.Expression;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class GetValueInvokeInjector implements Injector {

  private final static String EXPR_INTERNAL_NAME = Type.getInternalName(Expression.class);

  // receiver
  private MethodVisitor methodVisitor;

  public GetValueInvokeInjector(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, EXPR_INTERNAL_NAME, "getValue",
        "(Lorg/apache/commons/jxpath/JXPathContext;Ljava/lang/String;)Ljava/lang/Object;", false);
  }

}