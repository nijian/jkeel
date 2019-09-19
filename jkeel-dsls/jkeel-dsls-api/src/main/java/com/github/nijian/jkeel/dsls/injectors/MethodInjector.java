package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Context;
import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.MethodVisitor;

public class MethodInjector extends Injector {

    private int access;
    private String name;
    private String[] exceptions;
    private String retTypeSignature;
    private String[] argTypeSignatures;

    public MethodInjector(int access, String name, String[] exceptions, String retTypeSignature,
            String... argTypeSignatures) {
        this.access = access;
        this.name = name;
        this.exceptions = exceptions;
        this.retTypeSignature = retTypeSignature;
        this.argTypeSignatures = argTypeSignatures;
    }

    @Override
    public void execute(Context ctx, InjectorExecutor executor) {
        String descriptor = "(" + String.join("", argTypeSignatures) + ")" + retTypeSignature;
        String signature = "(" + String.join("", argTypeSignatures) + ")";
        MethodVisitor mv = ctx.getClassWriter().visitMethod(access, name, descriptor, signature, exceptions);
        ctx.setMethodVisitor(mv);
    }

}
