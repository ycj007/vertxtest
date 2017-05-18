package io.vertx.eventbus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
public class Run {


    public static void main(String[] args) {
       Vertx vertx = Vertx.vertx();


        vertx.deployVerticle(MyVerticleF.class.getName(), ar -> {

            if (ar.succeeded()) {

                vertx.deployVerticle(MyVerticleS.class.getName());

            }

        });

    }
}
