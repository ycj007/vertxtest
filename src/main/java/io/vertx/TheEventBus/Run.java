package io.vertx.TheEventBus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class Run {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        EventBus eb = vertx.eventBus();
        String address = "news";
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("some-header", "some-value");

        consumer(eb,address);
        eb.publish(address,"publish mes");
        eb.send(address,"send mes",options,ar->{

            if(ar.succeeded()){
                System.out.println(ar.result().body());
            }
        });




    }



    public static void consumer(EventBus eb,String target){
        eb.consumer(target,message -> {
            System.out.println(message.address());
            System.out.println(message.headers());
            System.out.println(message.body());
            message.reply("good");
        }).completionHandler(res -> {
            if (res.succeeded()) {
                System.out.println("The handler registration has reached all nodes");
            } else {
                System.out.println("Registration failed!");
            }
        });
    }
}
