package io.vertx;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class GitConfig {
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        ConfigStoreOptions git = new ConfigStoreOptions()
                .setType("git")
                .setConfig(new JsonObject()
                        .put("url", "https://github.com/cescoffier/vertx-config-test.git")
                        .put("path", "local")
                        .put("filesets",
                                new JsonArray().add(new JsonObject().put("pattern", "*.json"))));

        ConfigRetriever retriever = ConfigRetriever.create(vertx,
                new ConfigRetrieverOptions().addStore(git));
    }
}
