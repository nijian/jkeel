package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(CastInjector.class);

  private MethodVisitor mv;
  private Cast cast;
  private int index;
  private String value;

  public CastInjector(MethodVisitor mv, Cast cast) {
    this(mv, cast, -1);
  }

  public CastInjector(MethodVisitor mv, Cast cast, int index) {
    this(mv, cast, index, null);
  }

  public CastInjector(MethodVisitor mv, Cast cast, String value) {
    this(mv, cast, -1, value);
  }

  private CastInjector(MethodVisitor mv, Cast cast, int index, String value) {
    this.mv = mv;
    this.cast = cast;
    this.index = index;
    this.value = value;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    switch (cast) {
    case OBJECT_STRING:
      mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, OBJECT_INTERNAL_NAME, "toString", "()Ljava/lang/String;", false);
      break;
    case STRING_BIGDECIMAL:
      mv.visitTypeInsn(Opcodes.NEW, BIGDECIMAL_INTERNAL_NAME);
      mv.visitInsn(Opcodes.DUP);
      if (value != null) {
        mv.visitLdcInsn(value);
      } else {
        mv.visitVarInsn(Opcodes.ALOAD, index);
      }
      mv.visitMethodInsn(Opcodes.INVOKESPECIAL, BIGDECIMAL_INTERNAL_NAME, "<init>", "(Ljava/lang/String;)V", false);
      break;
    default:
      logger.error("Unsupported cast : {}", cast);
      throw new RuntimeException("Unsupported cast : " + cast);
    }

  }

}