package io.vertx.common;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class VertxUtils {

    private static class VertxHodler {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Vertx instance =  Vertx.vertx();
    }
    public static Vertx getDefaultVertx(){
        return VertxHodler.instance;
    }

    public static Vertx getVertx(VertxOptions options){

        return Vertx.vertx();
    }


}
