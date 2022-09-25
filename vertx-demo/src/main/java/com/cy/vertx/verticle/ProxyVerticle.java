package com.cy.vertx.verticle;

import com.cy.vertx.config.Server;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProxyVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    JsonObject config = config();
    int port = config.getInteger("port");
    JsonArray servers = config.getJsonArray("server");
    List<Server> list = new ArrayList<>(servers.size());
    for (Object server : servers) {
      Server actualServer = new Server((JsonObject) server);
      list.add(actualServer);
    }
    // Create the HTTP server
    vertx.createHttpServer().requestHandler(request -> {

      list.forEach(server -> {
        String location = server.getLocation();
        String path = request.path();
        if (matchRequestUrl( location, path)) {
          HttpClient httpClient = createHttpClient(server);
          requestMethod2(request, httpClient);
//        requestMethod1(request, httpClient);
        }
      });
    })
      // Start listening
      .listen(port)
      // Print the port
      .onSuccess(server ->
        System.out.println(
          "Proxy server started on port: " + server.actualPort()
        )
      );
  }

  private boolean matchRequestUrl(String location, String targetUrl) {
    return targetUrl.startsWith(location);
  }

  private HttpClient createHttpClient(Server server) {
    HttpClientOptions options = new HttpClientOptions();
    try {
      URL url = new URL(server.getProxyPass());
      options.setDefaultHost(url.getHost());
      options.setDefaultPort(url.getPort());
//      options.setDefaultHost("www.qq.com");
//      options.setDefaultPort(443);
//      options.setSsl(true);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return vertx.createHttpClient(options);
  }

  private void requestMethod2(HttpServerRequest request, HttpClient httpClient) {
    HttpServerResponse response = request.response();
    request.pause();
    System.out.println("path:" + request.path());
    System.out.println("uri:" + request.uri());

    httpClient.request(request.method(), request.uri(), ar -> {
      if (ar.succeeded()) {
        HttpClientRequest request2 = ar.result();
        request2.headers().setAll(request.headers());

        request2.send(request);
        request2.response(ar2 -> {
          if (ar2.succeeded()) {
            HttpClientResponse response2 = ar2.result();
            response.setStatusCode(response2.statusCode());
            response.headers().setAll(response2.headers());
            response2.handler(x -> {
              System.out.println("target server response:" + x.toString());
              response.write(x);
            });
            response2.endHandler(h -> {
              System.out.println("endHandler");
              response.end();
            });
          } else {
            ar2.cause().printStackTrace();
            response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
            response.setStatusMessage(ar2.cause().getMessage());
          }
        });


      } else {
        ar.cause().printStackTrace();
        response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        response.setStatusMessage(ar.cause().getMessage());
      }
    });
  }

  private void requestMethod1(HttpServerRequest request, HttpClient httpClient) {
    System.out.println("uri:" + request.uri());
    request.pause();
    HttpServerResponse response = request.response();
    httpClient.request(request.method(), request.uri(), ar -> {
      if (ar.succeeded()) {
        HttpClientRequest request2 = ar.result();
//        request2.setChunked(true);
        // 设置Header
        request2.headers().setAll(request.headers());
//        request.bodyHandler(h -> {
//          System.out.println("jjjj:"+h.toString());
//        });
//        request.pipeTo(request2);
//        request2.send(request);
        request2.send(request).onSuccess(response2 -> {
          System.out.println("success");
          response2.bodyHandler(h -> {
            response.end(h);
          });
        }).onFailure(err -> {
          System.out.println("onFailure");
          err.printStackTrace();
        });
      } else {
        ar.cause().printStackTrace();
        response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        response.setStatusMessage(ar.cause().getMessage());
      }
    });
  }
}
