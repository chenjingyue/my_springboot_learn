package com.cy.vertx.config;

import io.vertx.core.json.JsonObject;

public class Server {

  private String location;
  private String proxyPass;

  public Server(JsonObject config) {
    this.location = config.getString("location");
    this.proxyPass = config.getString("proxy_pass");
  }

  public Server(String location, String proxyPass) {
    this.location = location;
    this.proxyPass = proxyPass;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getProxyPass() {
    return proxyPass;
  }

  public void setProxyPass(String proxyPass) {
    this.proxyPass = proxyPass;
  }

  @Override
  public String toString() {
    return "Server{" +
      "location='" + location + '\'' +
      ", proxyPass='" + proxyPass + '\'' +
      '}';
  }
}
