package com.cy.vertx;

import io.vertx.core.Launcher;

public class MyLauncher extends Launcher {
  public static void main(String[] args) {
    MyLauncher myLauncher = new MyLauncher();
    myLauncher.dispatch(args);
//    Vertx.vertx().deployVerticle(new MainVerticle());
//    Vertx.vertx().deployVerticle(new ProxyVerticle());
  }

}
