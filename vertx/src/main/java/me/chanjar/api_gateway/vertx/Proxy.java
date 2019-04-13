package me.chanjar.api_gateway.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;

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
    vertx.deployVerticle(Proxy.class.getName());
  }

  @Override
  public void start() throws Exception {

    HttpClient client = vertx.createHttpClient(new HttpClientOptions());
    vertx.createHttpServer()
        .requestHandler(req -> {

//          System.out.println("Proxying request: " + req.uri());

          HttpClientRequest c_req = client.request(req.method(), REMOTE_PORT, REMOTE_HOST, req.uri(),
              c_res -> {
//                System.out.println("Proxying response: " + c_res.statusCode());
                req.response().setChunked(true);
                req.response().setStatusCode(c_res.statusCode());
                req.response().headers().setAll(c_res.headers());
                c_res.handler(data -> {
//                  System.out.println("Proxying response body: " + data.toString("ISO-8859-1"));
                  req.response().write(data);
                });
                c_res.endHandler((v) -> req.response().end());
              });

          c_req.setChunked(true);

          c_req.headers().setAll(req.headers());

          req.handler(data -> {
//            System.out.println("Proxying request body " + data.toString("ISO-8859-1"));
            c_req.write(data);
          });

          req.endHandler((v) -> c_req.end());

        }).listen(LOCAL_PORT);
  }
}
