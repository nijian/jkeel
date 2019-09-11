package com.github.nijian.jkeel.dsls.injectors;

import com.github.nijian.jkeel.dsls.Injector;
import com.github.nijian.jkeel.dsls.InjectorExecutor;

import org.objectweb.asm.ClassWriter;

public class MethodInjector implements Injector {

    private ClassWriter classWriter;
    private int access;
    private String name;
    private String descriptor;
    private String signature;
    private String[] exceptions;

    public MethodInjector(ClassWriter classWriter, int access, String name, String descriptor, String signature,
            String[] exceptions) {
        this.classWriter = classWriter;
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    @Override
    public void execute(InjectorExecutor executor) {
        classWriter.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
