package com.cy.vertx.verticle;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class FirstHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext context) {
    // Get the address of the request
    String address = context.request().connection().remoteAddress().toString();
    // Get the query parameter "name"
    MultiMap queryParams = context.queryParams();
    System.out.println(context.request().method());
    JsonObject bodyAsJson = context.getBodyAsJson();
//    System.out.println("headers:"+context.request().headers());
    System.out.println("FirstHandler recive body:" + bodyAsJson);
    String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
    // Write a json response
    if(null == bodyAsJson) {
      bodyAsJson = new JsonObject();
      bodyAsJson.put("message","param is null");
    }
    HttpServerResponse response = context.response();
    context.json(bodyAsJson.put("name",name)).onSuccess(success -> {
//      System.out.println("aaa:"+response.headers());
    }).onFailure(err -> {
      err.printStackTrace();
      response.setStatusCode(500);
      response.setStatusMessage(err.getMessage());
    });
//    context.json(
//      new JsonObject()
//        .put("name", name)
//        .put("address", address)
//        .put("message", "Hello " + name + " connected from " + address)
//    );
  }
}
