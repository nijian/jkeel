package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.ClassWriter;

public class MethodInjector implements Injector {

    private ClassWriter cw;
    private int access;
    private String name;
    private String[] exceptions;
    private String retTypeSignature;
    private String[] argTypeSignatures;

    public MethodInjector(ClassWriter cw, int access, String name, String[] exceptions, String retTypeSignature,
            String... argTypeSignatures) {
        this.cw = cw;
        this.access = access;
        this.name = name;
        this.exceptions = exceptions;
        this.retTypeSignature = retTypeSignature;
        this.argTypeSignatures = argTypeSignatures;
    }

    @Override
    public void execute(InjectorExecutor executor) {
        String descriptor = "(" + String.join("", argTypeSignatures) + ")" + retTypeSignature;
        String signature = "(" + String.join("", argTypeSignatures) + ")";
        cw.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
