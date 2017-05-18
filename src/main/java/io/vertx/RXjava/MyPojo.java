package io.vertx.RXjava;

import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by yuanchongjie on 2017/5/10.
 */
public class MyPojo implements Serializable{


    private String name;
    private String age;

    private MyPojo myPojo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public MyPojo getMyPojo() {
        return myPojo;
    }

    public void setMyPojo(MyPojo myPojo) {
        this.myPojo = myPojo;
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.put("name",getName());
        json.put("age",getAge());
        json.put("myPojo", Objects.toString(getMyPojo(),"null"));
        return json.toString();
    }
}
