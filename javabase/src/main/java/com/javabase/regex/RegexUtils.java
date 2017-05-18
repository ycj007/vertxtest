package com.javabase.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuanchongjie on 2017/5/16.
 */
public class RegexUtils {



    private static Pattern getPattern(String regex){

        if(null==regex){
            return null;
        }

        return Pattern.compile(regex);

    }

    private static Pattern getPattern(String regex,int flag){

        if(regex==null){
            return null;
        }


        return Pattern.compile(regex,flag);
    }


    public static  Matcher getMatcher(String input,String regex,int flags){

        return getPattern(regex,flags).matcher(input);
    }


    public static void main(String[] args) {

        String input = "Here you'll find all the documentation and tutorials written by the Spring team. You can also generate a new Spring Boot project in seconds by clicking...";
        String regex = "[a-z]";
        Matcher matcher = getMatcher(input,regex,0);
        boolean matched = matcher.matches();
        System.out.println("matched = " + matched);
        boolean find = matcher.find();
        System.out.println("find = " + find);
        String group = matcher.group();
        System.out.println("group = " + group);
        int groupsCount = matcher.groupCount();
        for (int i = 0; i < groupsCount; i++) {
            System.out.println("i = " + matcher.group(i));

        }
        regex = " ";
        String [] splits = getPattern(regex).split(input);
        for (String split : splits) {
            System.out.println("split = " + split);
        }



    }
}
