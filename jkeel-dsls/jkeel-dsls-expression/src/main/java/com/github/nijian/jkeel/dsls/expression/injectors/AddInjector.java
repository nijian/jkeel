package com.github.nijian.jkeel.dsls.expression.injectors;

import com.github.nijian.jkeel.dsls.InjectorExecutor;
import com.github.nijian.jkeel.dsls.expression.ExpressionMeta;
import com.github.nijian.jkeel.dsls.expression.MathOpInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInvokeInjector;

import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AddInjector extends MathOpInjector {

  private final static Logger logger = LoggerFactory.getLogger(AddInjector.class);

  protected final static String THIS_INTERNAL_NAME = Type.getInternalName(AddInjector.class);

  protected final static String THIS_SIGNATURE = Utility.getSignature(AddInjector.class.getName());

  // receiver
  private MethodVisitor methodVisitor;

  public final ExpressionMeta meta;

  public AddInjector(MethodVisitor methodVisitor, ExpressionMeta meta) {
    this.methodVisitor = methodVisitor;
    this.meta = meta;
  }

  /**
   * Always use BigDecimal to handle arithmetic operation
   */
  @Override
  public void execute(InjectorExecutor injectorExecutor) {
    if (meta != null) {
      logger.info("Expression Meta is : {}", meta);
      injectorExecutor.execute(new LoadInjector(methodVisitor, Object.class, 2));
      methodVisitor.visitFieldInsn(Opcodes.GETFIELD, EM_INTERNAL_NAME, "internalMathContext", MC_SIGNATURE);
    } else {
      methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, MC_INTERNAL_NAME, "DECIMAL64", MC_SIGNATURE);
    }
    injectorExecutor.execute(new MethodInvokeInjector(methodVisitor, Opcodes.INVOKEVIRTUAL, BIGDECIMAL_INTERNAL_NAME,
        "add", false, BIGDECIMAL_SIGNATURE, BIGDECIMAL_SIGNATURE, MC_SIGNATURE));
    logger.info("Injected BigDecimal add operation");
  }

}