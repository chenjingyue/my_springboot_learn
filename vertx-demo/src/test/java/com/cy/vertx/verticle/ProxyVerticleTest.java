package com.cy.vertx.verticle;

import groovy.json.JsonSlurper;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
class ProxyVerticleTest {

  @BeforeEach
  void setUp(Vertx vertx, VertxTestContext testContext) {

    DeploymentOptions deploymentOptions = new DeploymentOptions();
    File file = new File("G:\\GitCode\\my_springboot_learn\\vertx-demo\\src\\main\\resources\\config\\config.json");
    deploymentOptions.setConfig(new JsonObject((Map<String, Object>) new JsonSlurper().parse(file)));
    vertx.deployVerticle(new MainVerticle(), ar -> {
      if (ar.succeeded()) {
        vertx.deployVerticle(new ProxyVerticle(), deploymentOptions, testContext.succeedingThenComplete());
      }
    });
//    CompositeFuture.all()
  }

  @Test
  public void testTargetServer(Vertx vertx, VertxTestContext testContext) {
    WebClient webClient = WebClient.create(vertx);
    webClient.get(8888,"localhost", "/name").send().onSuccess(
      ok -> {
        JsonObject jsonObject = getJsonObject();
        assertThat(ok.bodyAsString()).isEqualTo(jsonObject.toString());
        testContext.completeNow();
      }
    ).onFailure(testContext::failNow);
  }

  @Test
  public void testProxyServer(Vertx vertx, VertxTestContext testContext) {
    HttpClient client = vertx.createHttpClient();
    client.request(HttpMethod.GET, 9999, "localhost", "/name")
      .compose(req -> req.send().compose(HttpClientResponse::body))
      .onComplete(testContext.succeeding(buffer -> testContext.verify(() -> {
//        System.out.println(buffer.toString());
        JsonObject jsonObject = getJsonObject();
        assertThat(buffer.toString()).isEqualTo(jsonObject.toString());
        testContext.completeNow();
      })));

  }

  private JsonObject getJsonObject() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.put("message", "param is null");
    jsonObject.put("name", "unknown");
    return jsonObject;
  }
}
