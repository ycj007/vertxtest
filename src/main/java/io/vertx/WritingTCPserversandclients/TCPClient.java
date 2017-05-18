package io.vertx.WritingTCPserversandclients;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class TCPClient  extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        NetClient client = Vertx.vertx().createNetClient();
        client.connect(8888,"localhost",ar -> {
            if(ar.succeeded()){
                System.out.println("connect success");
                vertx.setPeriodic(2000, h->{
                    ar.result().write("no").handler(data ->{
                        System.out.println("client  receive " + data.toString());
                    });;
                });


            }else{
                System.out.println(ar.cause());
            }

        });
    }

    public static void main(String[] args) {

        Vertx.vertx().deployVerticle(TCPServer.class.getName(),ar->{
            if(ar.succeeded()){
                Vertx.vertx().deployVerticle(TCPClient.class.getName());
            }else {
                System.out.println(ar.cause());
            }

        });




    }
}
