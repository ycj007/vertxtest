package io.vertx.ch1;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.file.FileSystem;

/**
 * Created by yuanchongjie on 2017/5/3.
 */
public class Begin {

    private static Vertx vertx = Vertx.vertx();

    public static void main(String[] args) {



        VertxOptions vertxOptions =new VertxOptions();
        vertxOptions.setWorkerPoolSize(1024);
        Vertx vertx =  getInstance(vertxOptions);

        vertx.setPeriodic(1000, id -> {
            // This handler will get called every second
            System.out.println("timer fired!");
        });
    }




    public static  Vertx getInstance(VertxOptions vertxOptions){

        if(vertxOptions!=null){

            System.out.println("TEst1");
            return Vertx.vertx(vertxOptions);
        }
        return vertx;

    }

    public static  Vertx getInstance1(VertxOptions vertxOptions){

        if(vertxOptions!=null){
            return Vertx.vertx(vertxOptions);
        }
        return vertx;

    }

    public static  Vertx getInstance2(VertxOptions vertxOptions){

        if(vertxOptions!=null){
            return Vertx.vertx(vertxOptions);
        }
        return vertx;

    }

    public static  Vertx getInstanceTest2(VertxOptions vertxOptions){

        if(vertxOptions!=null){
            return Vertx.vertx(vertxOptions);
        }
        return vertx;

    }

}
