package io.vertx.service.param;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.json.JsonObject;

/**
 * Created by yuanchongjie on 2017/5/12.
 */
@DataObject(generateConverter = true,inheritConverter=true)
public class Param  extends BaseParam{

    @GenIgnore
    private String name;
    private Integer age;

    public Param() {

    }

    public Param(JsonObject jsonObject) {
        ParamConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        ParamConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
