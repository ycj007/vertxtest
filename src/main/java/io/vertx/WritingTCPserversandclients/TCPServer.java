package io.vertx.WritingTCPserversandclients;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class TCPServer extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        NetServerOptions options = new NetServerOptions();
        NetServer netServer = Vertx.vertx().createNetServer(options);


        netServer.connectHandler(netSocket -> {
            netSocket.handler(buffer -> {

                System.out.println("server receive "+ buffer.toString());
                vertx.setPeriodic(2000, h->{
                    netSocket.write("yes");
                });

            });
            netSocket.closeHandler(v -> {
                System.out.println("The socket has been closed");
            });
            netSocket.exceptionHandler(e ->{
                System.out.println(e.fillInStackTrace());
            });
        });

        netServer.listen(8888,"localhost",server ->{
            if(server.succeeded()){
                System.out.println("listen success");
            }

            if(server.failed()){
                System.out.println(server.cause());
            }

        });

        startFuture.complete();
    }


}
