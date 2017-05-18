package io.vertx.Requestbodyhandling;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.*;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

import java.util.Set;

/**
 * Created by yuanchongjie on 2017/5/9.
 */
public class Controller extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        Route route = router.route();
        BodyHandler bodyHandler =BodyHandler.create();
        route.handler(bodyHandler);
        router.route().handler(CookieHandler.create());
        SessionStore store1 = LocalSessionStore.create(vertx);
        SessionHandler sessionHandler =  SessionHandler.create(store1);
        router.route().handler(sessionHandler);

        router.post("/some/path/uploads").handler(routingContext -> {
            Session session = routingContext.session();
            session.put("foo", "bar");
            //Set<FileUpload> uploads = routingContext.fileUploads();
            System.out.println(routingContext.getBodyAsJson());
            routingContext.addCookie(Cookie.cookie("othercookie", "somevalue"));

        });

        httpServer.requestHandler(router::accept).listen(8080);



    }
}
