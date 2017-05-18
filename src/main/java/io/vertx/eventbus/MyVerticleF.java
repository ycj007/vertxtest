package io.vertx.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.MessageProducer;

import java.util.Random;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
public class MyVerticleF extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        Vertx vertx1 = new Vertx(vertx);
        MessageProducer<String> messageProducer = vertx1.eventBus().sender("test");
        vertx1.periodicStream(1000).toObservable().subscribe(action -> {
            Random random = new Random();
            String msg = random.nextInt() + "";
            messageProducer.send(msg, ar -> {
                System.out.println("send " + msg + " success");
            });
        });
    }


}

