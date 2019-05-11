package me.chanjar.api_gateway.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 * copy from <a href="https://github.com/vert-x3/vertx-examples/blob/master/core-examples/src/main/java/io/vertx/example/core/http/proxy/Proxy.java">Vertx Examples - Porxy</a>
 */
public class Proxy extends AbstractVerticle {

  static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "9090"));
  static final String REMOTE_HOST = System.getProperty("remoteHost", "tomcat");
  static final int REMOTE_PORT = Integer.parseInt(System.getProperty("remotePort", "8080"));

  // Convenience method so you can run it in your IDE
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(new VertxOptions().setPreferNativeTransport(true));
    DeploymentOptions deploymentOptions = new DeploymentOptions()
        .setInstances(Runtime.getRuntime().availableProcessors());
    vertx.deployVerticle(Proxy.class.getName(), deploymentOptions);
  }

  @Override
  public void start() throws Exception {

    HttpClient client = vertx.createHttpClient(new HttpClientOptions());
    vertx.createHttpServer()
        .requestHandler(clientReq -> {

          HttpServerResponse clientResp = clientReq.response();
          //          System.out.println("Proxying request: " + clientReq.uri());
          HttpClientRequest originReq = client.request(clientReq.method(), REMOTE_PORT, REMOTE_HOST, clientReq.uri(),
              originResp -> {
//                System.out.println("Proxying response: " + originResp.statusCode());
                clientResp.setChunked(true);
                clientResp.setStatusCode(originResp.statusCode());
                clientResp.headers().setAll(originResp.headers());
                originResp.handler(data -> {
//                  System.out.println("Proxying response body: " + data.toString("ISO-8859-1"));
                  clientResp.write(data);
                });
                originResp.endHandler((v) -> clientResp.end());
              });

          originReq.setChunked(true);

          originReq.headers().setAll(clientReq.headers());

          clientReq.handler(data -> {
//            System.out.println("Proxying request body " + data.toString("ISO-8859-1"));
            originReq.write(data);
          });

          clientReq.endHandler((v) -> originReq.end());

        }).listen(LOCAL_PORT);
  }
}
