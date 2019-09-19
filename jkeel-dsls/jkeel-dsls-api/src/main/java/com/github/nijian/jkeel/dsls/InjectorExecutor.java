package com.github.nijian.jkeel.dsls;

public class InjectorExecutor {

  private Context ctx;

  public InjectorExecutor(Context ctx){
    this.ctx = ctx;
  }

  public void execute(Injector injector) {
    injector.setInjectorExecutor(this);
    injector.execute(ctx, this);
  }
  
}