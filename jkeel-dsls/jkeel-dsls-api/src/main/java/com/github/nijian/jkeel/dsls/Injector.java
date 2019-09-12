package com.github.nijian.jkeel.dsls;

import java.math.BigDecimal;

import org.apache.bcel.classfile.Utility;
import org.objectweb.asm.Type;

public interface Injector {

  String BIGDECIMAL_INTERNAL_NAME = Type.getInternalName(BigDecimal.class);

  String BIGDECIMAL_SIGNATURE = Utility.getSignature(BigDecimal.class.getName());

  void execute(InjectorExecutor executor);

}