package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.ClassWriter;

public class MethodInjector implements Injector {

    private ClassWriter classWriter;
    private int access;
    private String name;
    private String[] exceptions;
    private String retTypeSignature;
    private String[] argTypeSignatures;

    public MethodInjector(ClassWriter classWriter, int access, String name, String[] exceptions,
            String retTypeSignature, String... argTypeSignatures) {
        this.classWriter = classWriter;
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
        classWriter.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
