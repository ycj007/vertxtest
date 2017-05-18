package io.vertx.WritingHTTPserversandclients;

import io.vertx.common.VertxUtils;
import io.vertx.core.http.HttpClient;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class Run {

    public static void main(String[] args) {
        VertxUtils.getDefaultVertx().deployVerticle(HttpServerVerticle.class.getName());

    }
}
