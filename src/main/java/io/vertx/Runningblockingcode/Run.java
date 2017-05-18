package io.vertx.Runningblockingcode;


import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class Run {


    public static void main(String[] args) {

        /*Vertx vertx = VertxUtils.getDefaultVertx();
        way1(vertx);
        System.out.println("test");*/

        Executor executor = Executors.newCachedThreadPool();

        executor.execute(() -> {

            System.out.println("test.......");
        });


    }


    public static void way1(Vertx vertx) {

        vertx.executeBlocking(

                p
                        ->
                {
                    for (int i = 0; i < 10000; i++) {
                        System.out.println(i);

                    }
                    p.complete(1000);

                }, p
                        ->
                {
                    System.out.println("result!" + p.result());
                }
        );
        //vertx.close();
    }

    public static void way2(Vertx vertx) {
        WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-worker-pool");

        executor.executeBlocking(future -> {
            for (int i = 0; i < 10; i++) {

                System.out.println(i);

            }
            future.complete(1000);
        }, asyncResult -> {
            System.out.println("result!" + asyncResult.result());
        });
        executor.close();
    }

}
