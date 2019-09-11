package com.github.nijian.jkeel.dsls.injectors;

import java.math.BigDecimal;

import com.github.nijian.jkeel.dsls.Cast;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastInjector implements Injector {

  private static Logger logger = LoggerFactory.getLogger(CastInjector.class);

  private MethodVisitor methodVisitor;
  private Cast cast;
  private Class<?> numberType;

  public CastInjector(MethodVisitor methodVisitor, Cast cast) {
    this.methodVisitor = methodVisitor;
    this.cast = cast;
  }

  public CastInjector(MethodVisitor methodVisitor, Cast cast, Class<?> numberType) {
    this.methodVisitor = methodVisitor;
    this.cast = cast;
    this.numberType = numberType;
  }

  @Override
  public void execute(InjectorExecutor executor) {
    switch (cast) {
    case OBJECT_STRING:
      methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;",
          false);
      break;
    case STRING_NUMBER:
      if (numberType.isAssignableFrom(Integer.class)) {
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I",
            false);
      } else if (numberType.isAssignableFrom(Long.class)) {

      } else if (numberType.isAssignableFrom(Float.class)) {

      } else if (numberType.isAssignableFrom(Double.class)) {

      } else if (numberType.isAssignableFrom(BigDecimal.class)) {

      } else {
        logger.error("Unsupported number type : {}", numberType);
        throw new RuntimeException("Unsupported number type : " + numberType);
      }

      break;
    default:
      throw new RuntimeException("xxx");
    }

  }

}