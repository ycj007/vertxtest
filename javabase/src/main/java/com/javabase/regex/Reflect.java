package com.javabase.regex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by yuanchongjie on 2017/5/16.
 */
public class Reflect {


    public static void main(String[] args) {
        Class c = null;
        try {
            c = Class.forName("org.apache.curator.framework.imps.CuratorFrameworkImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Constructor[ ] constructors  =  c.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println("constructor = " + constructor);
        }

        Field[] fields = c.getFields();

        for (Field field : fields) {
            System.out.println("field = " + field);

        }

        Method[] methods = c.getMethods();
        for (Method method : methods) {
            System.out.println("method = " + method);
            int modifiers = method.getModifiers();
            System.out.println("modifiers = " + modifiers);
            System.out.println("modifiers is public  = " +  Modifier.isPublic(modifiers));
        }




    }

}
