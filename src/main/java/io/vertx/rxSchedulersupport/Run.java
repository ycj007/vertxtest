package io.vertx.rxSchedulersupport;

import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.rx.java.RxHelper;
import rx.Observable;
import rx.Scheduler;

import java.util.concurrent.TimeUnit;

/**
 * Created by yuanchongjie on 2017/5/9.
 */
public class Run {
    public static void main(String[] args) {


       Vertx vertx  = Vertx.vertx();
        Scheduler scheduler = RxHelper.scheduler(vertx);

        // Create a periodic event stream using Vertx scheduler
        Observable<Long> o = Observable.
                timer(0, 1000, TimeUnit.MILLISECONDS, scheduler);

        o.subscribe(item -> {
            System.out.println("Got item " + item);
        });
    }
}
