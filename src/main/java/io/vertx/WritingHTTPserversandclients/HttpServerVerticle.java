package io.vertx.WritingHTTPserversandclients;

import com.sun.activation.registries.MimeTypeEntry;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;

import javax.activation.MimeType;


/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(1000000)
                .setLogActivity(true).setCompressionSupported(true);

        HttpServer server = vertx.createHttpServer(options);
        server.requestHandler(request ->{
            HttpServerResponse response = request.response();
            response.setChunked(true);

            request.response().sendFile("bar.txt", 0, 10);
          /*  Buffer totalBuffer = Buffer.buffer();*/
           /* System.out.println(request.version());
            System.out.println(request.method());
            System.out.println(request.uri());
            System.out.println(request.path());
            System.out.println(request.query());
            System.out.println(request.headers().toString());
            System.out.println(request.host());
            System.out.println(request.params());
            System.out.println(request.remoteAddress());
            System.out.println(request.absoluteURI());*/
           /* request.setExpectMultipart(true);
            request.handler(buffer -> {
                *//*System.out.println("I have received a chunk of the body of length " + buffer.length());
                totalBuffer.appendBuffer(buffer);*//*
            });

            request.endHandler(v -> {
               *//* System.out.println("Full body received, length = " + totalBuffer.length() + " content :"+totalBuffer.toString());
                response.write(totalBuffer).end();*//*
                MultiMap formAttributes = request.formAttributes();
                System.out.println(formAttributes.toString());
               *//* MultiMap headers = response.headers();

                headers.set("content-type", "text/html");*//*


            });*/

        });

        server.listen(8888,"localhost",s ->{

            if(s.succeeded()){
                System.out.println("listen 8888 success");
            }else{
                System.out.println(s.cause());
            }
        });
    }
}
