package io.vertx.Verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class MyVerticles extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        this.start();
        System.out.println("a start");
        vertx.deployVerticle("io.vertx.Verticles.SubVerticles", res -> {
            if (res.succeeded()) {
                System.out.println("success");
                startFuture.complete();
            } else {
                System.out.println("failed");
                startFuture.fail(res.cause());
            }
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        this.stop();
        System.out.println("a stop");
        super.stop(stopFuture);
    }

    @Override
    public void start() throws Exception {
        System.out.println("start");
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stop");
    }
}
