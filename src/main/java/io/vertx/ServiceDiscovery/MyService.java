package io.vertx.ServiceDiscovery;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class MyService  extends AbstractVerticle{


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void start() throws Exception {
        super.start();
        ServiceDiscovery serviceDiscovery = ServiceDiscovery.create(vertx);
        name="test";
        publishService(serviceDiscovery);

    }


    public static  void publishService(ServiceDiscovery discovery){

        Record record = new Record()
                .setType("eventbus-service-proxy")
                .setLocation(new JsonObject().put("endpoint", "the-service-address"))
                .setName("my-service")
                .setMetadata(new JsonObject().put("some-label", "some-value"));

        discovery.publish(record, ar -> {
            if (ar.succeeded()) {
                // publication succeeded
                Record publishedRecord = ar.result();
                System.out.println("publish success");
            } else {
                // publication failed
            }
        });

    }
}
