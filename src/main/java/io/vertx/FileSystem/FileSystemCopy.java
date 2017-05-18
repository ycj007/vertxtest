package io.vertx.FileSystem;

import io.vertx.common.VertxUtils;
import io.vertx.core.file.FileSystem;

/**
 * Created by yuanchongjie on 2017/5/8.
 */
public class FileSystemCopy {


    public static void main(String[] args) {

        FileSystem fileSystem  = VertxUtils.getDefaultVertx().fileSystem();

        fileSystem.copyBlocking("bar.txt","foo2.txt");


    }

}
