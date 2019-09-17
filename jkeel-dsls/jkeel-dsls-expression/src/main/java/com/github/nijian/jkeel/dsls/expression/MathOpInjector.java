package com.github.nijian.jkeel.dsls.expression;

import java.math.MathContext;

import com.github.nijian.jkeel.dsls.Injector;

import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.Type;

public abstract class MathOpInjector implements Injector {

  protected final static String MC_INTERNAL_NAME = Type.getInternalName(MathContext.class);

  protected final static String MC_SIGNATURE = Utility.getSignature(MathContext.class.getName());

  protected final static String EM_INTERNAL_NAME = Type.getInternalName(ExpressionMeta.class);

  protected final static String EM_SIGNATURE = Utility.getSignature(ExpressionMeta.class.getName());

}