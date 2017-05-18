package io.vertx.Handlingrequestsandcallingthenexthandler;

import io.vertx.core.Vertx;

/**
 * Created by yuanchongjie on 2017/5/9.
 */
public class Run {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(Controller.class.getName());
    }
}
