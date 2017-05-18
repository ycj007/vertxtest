package io.vertx.Verticles;

import io.vertx.common.VertxUtils;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.RxHelper;
import rx.Observable;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class Run {
    public static void main(String[] args) {


        Vertx vertx = VertxUtils.getDefaultVertx();

        JsonObject config = new JsonObject().put("name", "tim").put("directory", "/blah");
        DeploymentOptions options = new DeploymentOptions().setConfig(config);
        options.setIsolationGroup("test");
      /*  vertx.deployVerticle(MyVerticles.class.getName(),options,

                ar ->{
            if(ar.succeeded()){
                System.out.println("deploy success");
            }

            if(ar.failed()){
                System.out.println("deploy failed");
            }

                }
                );*/
        Observable<String>  observable =RxHelper.deployVerticle(io.vertx.rxjava.core.Vertx.vertx(),new MyVerticles());
        observable.subscribe(resp -> {
            // Got response
            System.out.println("ok");
        }, err -> {
            System.out.println("error");
            // Something went wrong
        });

        Context context = vertx.getOrCreateContext();
        if (context.isEventLoopContext()) {
            System.out.println("Context attached to Event Loop");
        } else if (context.isWorkerContext()) {
            System.out.println("Context attached to Worker Thread");
        } else if (context.isMultiThreadedWorkerContext()) {
            System.out.println("Context attached to Worker Thread - multi threaded worker");
        } else if (!Context.isOnVertxThread()) {
            System.out.println("Context not attached to a thread managed by vert.x");
        }


    }
}
