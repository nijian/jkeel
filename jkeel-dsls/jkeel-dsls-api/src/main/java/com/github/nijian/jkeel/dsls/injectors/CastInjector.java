package com.github.nijian.jkeel.dsls.injectors;

import java.math.BigDecimal;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(CastInjector.class);

  private MethodVisitor methodVisitor;
  private Cast cast;
  private int index;
  private String value;

  public CastInjector(MethodVisitor methodVisitor, Cast cast) {
    this(methodVisitor, cast, -1);
  }

  public CastInjector(MethodVisitor methodVisitor, Cast cast, int index) {
    this(methodVisitor, cast, index, null);
  }

  public CastInjector(MethodVisitor methodVisitor, Cast cast, String value) {
    this(methodVisitor, cast, -1, value);
  }

  private CastInjector(MethodVisitor methodVisitor, Cast cast, int index, String value) {
    this.methodVisitor = methodVisitor;
    this.cast = cast;
    this.index = index;
    this.value = value;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    switch (cast) {
    case OBJECT_STRING:
      methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, OBJECT_INTERNAL_NAME, "toString", "()Ljava/lang/String;",
          false);
      break;
    case STRING_BIGDECIMAL:
      methodVisitor.visitTypeInsn(Opcodes.NEW, Type.getInternalName(BigDecimal.class));
      methodVisitor.visitInsn(Opcodes.DUP);
      if (value != null) {
        methodVisitor.visitLdcInsn(value);
      } else {
        methodVisitor.visitVarInsn(Opcodes.ALOAD, index);
      }
      methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(BigDecimal.class), "<init>",
          "(Ljava/lang/String;)V", false);
      break;
    default:
      logger.error("Unsupported cast : {}", cast);
      throw new RuntimeException("Unsupported cast : " + cast);
    }

  }

}