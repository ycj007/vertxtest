package io.vertx.zookeeper.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class Barrie extends SyncPrimitive {

    private String name;
    private int size;
    Barrie(String address,String name,int size) throws KeeperException, IOException, InterruptedException {
        super(address);
        this.root = name;
        this.size = size;

// Create barrier node
        if (zk != null) {
            Stat s = zk.exists(root, false);
            if (s == null) {
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, 0);
            }
        }

// My node name
        name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
    }
}
