package com.github.nijian.jkeel.dsls;

public interface Injector extends ClassInfoAware {

  void execute(InjectorExecutor executor);

}