package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExprClassInfoAware;
import com.github.nijian.jkeel.dsls.expression.ExpressionInjection;
import com.github.nijian.jkeel.dsls.expression.ExpressionMeta;
import com.github.nijian.jkeel.dsls.injectors.LoadLocalVarInjector;
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

public class ExecuteMethodInjector implements Injector, ExprClassInfoAware {

  private static Logger logger = LoggerFactory.getLogger(ExecuteMethodInjector.class);

  private ClassWriter cw;
  private ParseTree tree;
  private ParseTreeWalker walker;
  private ExpressionMeta meta;

  public ExecuteMethodInjector(ClassWriter cw, ParseTree tree, ParseTreeWalker walker, ExpressionMeta meta) {
    this.cw = cw;
    this.tree = tree;
    this.walker = walker;
    this.meta = meta;
  }

  @Override
  public void execute(InjectorExecutor executor) {

    Class<?> retType = meta.getRetType();
    String retTypeSignature = Utility.getSignature(retType.getName());
    String executeMethodSignature = String.format(EXECUTE_SIGNATURE_TEMPLATE, retTypeSignature);

    logger.info("executeMethodSignature : {}", executeMethodSignature);
    // have to get MethodVisitor by this way
    MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "execute", executeMethodSignature, executeMethodSignature,
        null);
    walker.walk(new ExpressionInjection(executor, mv, meta), tree);
    executor.execute(new ReturnInjector(mv));

    // super execute method
    String superExecuteMethodSignature = String.format(EXECUTE_SIGNATURE_TEMPLATE, OBJECT_SIGNATURE);
    mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "execute", superExecuteMethodSignature, superExecuteMethodSignature, null);

    executor.execute(new LoadLocalVarInjector(mv, 0, 1, 2));
    executor.execute(new MethodInvokeInjector(mv, Opcodes.INVOKEVIRTUAL, meta.getName(), "execute", false,
        retTypeSignature, JXPATHCONTEXT_SIGNATURE, EM_SIGNATURE));
    executor.execute(new ReturnInjector(mv));

  }
}