package io.vertx.service;

import io.vertx.codegen.annotations.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.service.param.Param;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
@VertxGen
@ProxyGen
public interface CodeTest {

    void insert(JsonObject someData);
    @CacheReturn
    @Fluent
    CodeTest update(Param param);

    void commit(Handler<AsyncResult<Void>> resultHandler);

    @ProxyClose
    void close();
}
