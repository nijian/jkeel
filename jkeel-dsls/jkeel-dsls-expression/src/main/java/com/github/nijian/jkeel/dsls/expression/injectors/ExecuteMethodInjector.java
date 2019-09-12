package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.Const;
import com.github.nijian.jkeel.dsls.expression.ExpressionInjection;
import com.github.nijian.jkeel.dsls.expression.ExpressionMeta;
import com.github.nijian.jkeel.dsls.injectors.LoadInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInvokeInjector;
import com.github.nijian.jkeel.dsls.injectors.ReturnInjector;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteMethodInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(ExecuteMethodInjector.class);

  private ClassWriter classWriter;
  private ParseTree tree;
  private ParseTreeWalker walker;
  private ExpressionMeta meta;

  public ExecuteMethodInjector(ClassWriter classWriter, ParseTree tree, ParseTreeWalker walker, ExpressionMeta meta) {
    this.classWriter = classWriter;
    this.tree = tree;
    this.walker = walker;
    this.meta = meta;
  }

  @Override
  public void execute(InjectorExecutor injectorExecutor) {

    Class<?> retType = meta.getRetType();
    String retTypeSignature = Utility.getSignature(retType.getName());
    String executeMethodSignature = String.format(Const.EXECUTE_SIGNATURE_TEMPLATE, retTypeSignature);

    logger.info("executeMethodSignature : {}", executeMethodSignature);
    MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute", executeMethodSignature,
        executeMethodSignature, null);

    walker.walk(new ExpressionInjection(injectorExecutor, methodVisitor, meta), tree);
    injectorExecutor.execute(new ReturnInjector(methodVisitor));

    // super execute method
    String superExecuteMethodSignature = String.format(Const.EXECUTE_SIGNATURE_TEMPLATE, OBJECT_SIGNATURE);
    methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "execute", superExecuteMethodSignature,
        superExecuteMethodSignature, null);

    injectorExecutor.execute(new LoadInjector(methodVisitor, Object.class, 0, 1));
    injectorExecutor.execute(new MethodInvokeInjector(methodVisitor, Opcodes.INVOKEVIRTUAL, meta.getName(), "execute",
        false, retTypeSignature, JXPATHCONTEXT_SIGNATURE));
    injectorExecutor.execute(new ReturnInjector(methodVisitor));

  }
}