package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.Const;
import com.github.nijian.jkeel.dsls.expression.ExpressionInjection;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ExecuteMethodInjector implements Injector {

  private ClassWriter classWriter;
  private ParseTree tree;
  private ParseTreeWalker walker;
  private String executeMethodSignature;
  

  public ExecuteMethodInjector(ClassWriter classWriter, ParseTree tree, ParseTreeWalker walker,
      String executeMethodSignature) {
    this.classWriter = classWriter;
    this.tree = tree;
    this.walker = walker;
    this.executeMethodSignature = executeMethodSignature;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute", executeMethodSignature,
        executeMethodSignature, null);

    walker.walk(new ExpressionInjection(executor, methodVisitor, Integer.class), tree);
    
    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;",
        false);

    methodVisitor.visitInsn(Opcodes.ARETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    // super execute method
    String objectTypeSignature = Utility.getSignature(Object.class.getName());
    String superExecuteMethodSignature = String.format(Const.EXECUTE_SIGNATURE_TEMPLATE, objectTypeSignature);
    methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute", superExecuteMethodSignature,
        superExecuteMethodSignature, null);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HelloWorld", "execute",
        "(Lorg/apache/commons/jxpath/JXPathContext;)Ljava/lang/Integer;", false);
    methodVisitor.visitInsn(Opcodes.ARETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

  }
}