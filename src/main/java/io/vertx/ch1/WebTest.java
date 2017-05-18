package io.vertx.ch1;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * Created by yuanchongjie on 2017/5/3.
 */
public class WebTest {
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        Future<String> future = Future.future();

        vertx.runOnContext(t ->{
            System.out.println("test");

            future.complete("success");
        });
        future.setHandler(ar ->{

            System.out.println(ar.result());
        });

    }

}
