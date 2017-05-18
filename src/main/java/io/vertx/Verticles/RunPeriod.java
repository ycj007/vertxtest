package io.vertx.Verticles;

import io.vertx.core.Vertx;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class RunPeriod {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        long timerID = vertx.setTimer(1000, id -> {
            System.out.println("And one second later this is printed");
        });
        System.out.println(timerID);
        long timerID2 = vertx.setPeriodic(1000, id -> {
            System.out.println("And one second later this is printed");
        });
        System.out.println(timerID2);

        vertx.setTimer(5000,id -> {
            vertx.cancelTimer(timerID2);
        });

    }
}
