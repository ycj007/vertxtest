package io.vertx.ServiceDiscovery;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class GetService extends AbstractVerticle{


    private String name;

    @Override
    public void start() throws Exception {
        super.start();
        ServiceDiscovery serviceDiscovery = ServiceDiscovery.create(vertx);
        name="test";
        getService(serviceDiscovery);

    }


    public static  void getService(ServiceDiscovery discovery){
        discovery.getRecord(new JsonObject().put("name", "my-service"),ar->{

            if(ar.succeeded()){
                Record record = ar.result();
                ServiceReference serviceReference = discovery.getReference(record);
                MyService myService =serviceReference.getAs(MyService.class);
                System.out.println(myService.getName());
            }else{
                System.out.println(ar.cause());
            }
        });




    }
}
