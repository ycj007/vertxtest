package io.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class MainVerticle extends AbstractVerticle{
    public static void main(String[] args) {

        Vertx.vertx().deployVerticle(MainVerticle.class.getName());
    }

    @Override
    public void start() throws Exception {
        super.start();

        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", "127.0.0.1");
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));
        ClusterManager mgr = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                System.out.println("success");
            } else {
                // failed!
            }
        });

    }
}
