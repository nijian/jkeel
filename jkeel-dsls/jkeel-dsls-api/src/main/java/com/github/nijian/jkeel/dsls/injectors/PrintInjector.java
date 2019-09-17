package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PrintInjector implements Injector {


  private MethodVisitor methodVisitor;

  public PrintInjector(MethodVisitor methodVisitor) {
    this.methodVisitor = methodVisitor;
  }

  @Override
  public void execute(InjectorExecutor executor) {

    methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    methodVisitor.visitInsn(Opcodes.DUP);
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;",
        false);
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
        false);

  }

}