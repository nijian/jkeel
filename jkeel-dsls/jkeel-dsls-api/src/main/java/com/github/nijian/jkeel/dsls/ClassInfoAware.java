package com.github.nijian.jkeel.dsls;

import java.math.BigDecimal;

import org.apache.bcel.classfile.Utility;
import org.apache.commons.jxpath.JXPathContext;
import org.objectweb.asm.Type;

public interface ClassInfoAware {

  String OBJECT_INTERNAL_NAME = Type.getInternalName(Object.class);

  String OBJECT_SIGNATURE = Utility.getSignature(Object.class.getName());

  String STRING_INTERNAL_NAME = Type.getInternalName(String.class);

  String STRING_SIGNATURE = Utility.getSignature(String.class.getName());

  String BIGDECIMAL_INTERNAL_NAME = Type.getInternalName(BigDecimal.class);

  String BIGDECIMAL_SIGNATURE = Utility.getSignature(BigDecimal.class.getName());

  String JXPATHCONTEXT_SIGNATURE = Utility.getSignature(JXPathContext.class.getName());

}