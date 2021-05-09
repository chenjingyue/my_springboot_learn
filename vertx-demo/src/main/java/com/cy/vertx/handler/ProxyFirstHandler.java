package com.cy.vertx.handler;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProxyFirstHandler implements Handler<HttpServerRequest> {

  private Vertx vertx;
  public ProxyFirstHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(HttpServerRequest request) {
    // Get the address of the request
    String path = request.path();
    System.out.println("path:" + path);
    HttpServerResponse response = request.response();

    HttpClientOptions options = new HttpClientOptions();
    options.setDefaultHost("127.0.0.1");
    options.setDefaultPort(8888);
    HttpClient httpClient = vertx.createHttpClient(options);

    request.pause();

    httpClient.request(request.method(),request.uri(),ar -> {
      if (ar.succeeded()) {
        System.out.println("ar success");
        HttpClientRequest request2 = ar.result();

        request.handler(h -> {
          System.out.println("request.handler");
          request2.write(h);
        });
//        request2.headers().setAll(request.headers());

        request2.response(ar2 -> {
          System.out.println("request2.response");
          if (ar2.succeeded()) {
            System.out.println("ar2 success");
            HttpClientResponse response2 = ar2.result();
            response.setStatusCode(response2.statusCode());
            response2.bodyHandler(x -> {
              response.write(x);
            });
            response2.endHandler(h -> {
              response.end();
            });
          } else {
            ar2.cause().printStackTrace();
            response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
            response.setStatusMessage(ar2.cause().getMessage());
          }
        });

//        request.endHandler(h -> {
//          System.out.println("request.endHandler");
//          request2.end();
//        });

      } else {
        ar.cause().printStackTrace();
        response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        response.setStatusMessage(ar.cause().getMessage());
      }
    });


  }
}
