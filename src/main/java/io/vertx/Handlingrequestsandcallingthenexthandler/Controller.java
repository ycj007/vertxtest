package io.vertx.Handlingrequestsandcallingthenexthandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * Created by yuanchongjie on 2017/5/9.
 */
public class Controller extends AbstractVerticle{

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        Route route =router.route().path("/test");
        Route route1 = router.route(HttpMethod.POST, "/catalogue/products/:producttype/:productid/");
        Route route2 = router.route().pathRegex(".*foo");
        /*route2.handler(routingContext -> {
            System.out.println(routingContext.request().path());
            routingContext.response().end("ok");
        });*/

        route2.pathRegex("\\/([^\\/]+)\\/([^\\/]+)").handler(routingContext -> {

            String productType = routingContext.request().getParam("param0");
            String productID = routingContext.request().getParam("param1");
            StringBuilder sb = new StringBuilder();
            sb.append("productType:");
            sb.append(productType);
            sb.append("productID:");
            sb.append(productID);
            routingContext.response().setChunked(true);
            routingContext.response().write(sb.toString()).end();

        });

        route.handler( routingContext -> {

            routingContext.response().end("hello");
        });
        route1.handler(routingContext ->{

            String productType = routingContext.request().getParam("producttype");
            String productID = routingContext.request().getParam("productid");
            System.out.println("productType:"+productType);
            System.out.println("productID:"+productID);
            StringBuilder sb = new StringBuilder();
            sb.append("productType:");
            sb.append(productType);
            sb.append("productID:");
            sb.append(productID);
            routingContext.response().setChunked(true);
            routingContext.response().write(sb.toString()).end();


        });
        server.requestHandler(router::accept).listen(8080);



    }
}
