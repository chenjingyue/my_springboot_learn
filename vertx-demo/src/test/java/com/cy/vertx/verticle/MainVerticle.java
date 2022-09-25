package com.cy.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    // Create a Router
    Router router = Router.router(vertx);

    // Mount the handler for all incoming requests at every path and HTTP method
    router.route(HttpMethod.GET,"/name").handler(new FirstHandler());
    router.route(HttpMethod.POST,"/name").handler(BodyHandler.create()).handler(new FirstHandler());
    router.route(HttpMethod.POST,"/hello").handler(BodyHandler.create()).handler(new FirstHandler());

    // Create the HTTP server
    vertx.createHttpServer()
      // Handle every request using the router
      .requestHandler(router)
      // Start listening
      .listen(8888)
      // Print the port
      .onSuccess(server ->
        System.out.println(
          "Target server started on port " + server.actualPort()
        )
      );

  }
}
