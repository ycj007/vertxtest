package io.vertx.service.param;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
//@DataObject(generateConverter = true)
public class BaseParam {

    private String id;

    private String version;

    public BaseParam() {

    }
    public BaseParam(JsonObject jsonObject) {
        //ParamConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        //ParamConverter.toJson(this, jsonObject);
        return jsonObject;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
