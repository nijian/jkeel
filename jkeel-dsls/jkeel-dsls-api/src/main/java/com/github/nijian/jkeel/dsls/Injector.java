package com.github.nijian.jkeel.dsls;

import com.github.nijian.jkeel.dsls.injectors.CastInjector;
import com.github.nijian.jkeel.dsls.injectors.ClassInjector;
import com.github.nijian.jkeel.dsls.injectors.ConstructorInjector;
import com.github.nijian.jkeel.dsls.injectors.GetInstanceFieldInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadConstInjector;
import com.github.nijian.jkeel.dsls.injectors.LoadLocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.LocalVarInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInjector;
import com.github.nijian.jkeel.dsls.injectors.MethodInvokeInjector;
import com.github.nijian.jkeel.dsls.injectors.ReturnInjector;

import org.objectweb.asm.Opcodes;

public abstract class Injector implements ClassInfoAware {

  protected Context<?> ctx;

  protected InjectorExecutor executor;

  public void setInjectorExecutor(InjectorExecutor executor) {
    this.executor = executor;
  }

  protected void CLASS(String name, String signature, String superName) {
    executor.execute(new ClassInjector(Opcodes.V1_8, Opcodes.ACC_PUBLIC, name, signature, superName, null));
  }

  protected void CONSTRUCTOR(String name) {
    executor.execute(new ConstructorInjector(name));
  }

  protected void PUBLIC_METHOD(String name, String[] exceptions, String retTypeSignature, String... argTypeSignatures) {
    executor.execute(new MethodInjector(Opcodes.ACC_PUBLIC, name, exceptions, retTypeSignature, argTypeSignatures));
  }

  protected void INVOKE(String owner, String name, String retSignature, String... argsSignatures) {
    executor.execute(new MethodInvokeInjector(Opcodes.INVOKEVIRTUAL, owner, name, false, retSignature, argsSignatures));
  }

  protected void GETFIELD(String owner, String name, String fieldSignature) {
    executor.execute(new GetInstanceFieldInjector(owner, name, fieldSignature));
  }

  protected void VAR(int index) {
    executor.execute(new LocalVarInjector(index));
  }

  protected void LDC(String str) {
    executor.execute(new LoadConstInjector(str));
  }

  protected void LOAD(int... indexes) {
    executor.execute(new LoadLocalVarInjector(indexes));
  }

  protected void RETURN() {
    executor.execute(new ReturnInjector());
  }

  protected void CAST(Cast cast) {
    executor.execute(new CastInjector(cast));
  }

  protected void CAST(Cast cast, int index) {
    executor.execute(new CastInjector(cast, index));
  }

  protected void CAST(Cast cast, String value) {
    executor.execute(new CastInjector(cast, value));
  }

  protected abstract void execute(Context<?> ctx, InjectorExecutor executor);

}