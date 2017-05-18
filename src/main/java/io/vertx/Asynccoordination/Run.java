package io.vertx.Asynccoordination;

import io.vertx.common.VertxUtils;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class Run {

    public static void main(String[] args) {
        Vertx vertx = VertxUtils.getDefaultVertx();

        //Concurrentcomposition(vertx);
        Sequentialcomposition();

    }

    public static void Concurrentcomposition() {

        CompositeFuture.all(getFutures()).setHandler(ar -> {
            if (ar.succeeded()) {
                System.out.println("All servers started");
            } else {
                System.out.println("At least one server failed");
            }
        });

    }


    public static  void Sequentialcomposition(){
        Vertx vertx = VertxUtils.getDefaultVertx();
        FileSystem fs = vertx.fileSystem();
        Future<Void> startFuture = Future.future();

        Future<Void> fut1 = Future.future();
        fs.createFile("foo.txt", fut1.completer());

        fut1.compose(v -> {
            // When the file is created (fut1), execute this:
            System.out.println("create foo.txt ");
            Future<Void> fut2 = Future.future();
            fs.writeFile("foo.txt", Buffer.buffer("test"), fut2.completer());
            return fut2;
        }).compose(v -> {
                    // When the file is written (fut2), execute this:
                    System.out.println("file is written ");
                    fs.move("foo.txt", "bar.txt", startFuture.completer());
                },
                // mark startFuture it as failed if any step fails.
                startFuture);
        startFuture.setHandler(ar ->{
           if(ar.succeeded()){
               System.out.println("success");
           }
           if(ar.failed()){
               System.out.println("failed");
               System.out.println(ar.cause());
           }

        });



    }


    public static List<Future> getFutures(){
        Vertx vertx = VertxUtils.getDefaultVertx();
        HttpServer httpServer = vertx.createHttpServer();
        NetServer netServer = vertx.createNetServer();
        netServer.connectHandler(socket ->{
            System.out.println("socket");
        } );

        Future<HttpServer> httpServerFuture = Future.future();
        httpServer.requestHandler(request ->{
            System.out.println("get");
        });
        httpServer.listen(httpServerFuture.completer());

        Future<NetServer> netServerFuture = Future.future();
        netServer.listen(netServerFuture.completer());
        return Arrays.asList(netServerFuture,httpServerFuture);
    }
}
