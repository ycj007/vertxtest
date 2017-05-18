package io.vertx.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
public class MyVerticleS extends AbstractVerticle {
    /**
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
        super.start();
        vertx.setPeriodic(1000, action->{

            Vertx vertx1 = new Vertx(vertx);
            vertx1.eventBus().consumer("test",msg ->{
                msg.reply("ok");
                System.out.println("receive  "+msg.body());
            });
        });
    }
}
