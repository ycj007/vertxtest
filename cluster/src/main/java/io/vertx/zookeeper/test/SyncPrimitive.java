package io.vertx.zookeeper.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class SyncPrimitive implements Watcher {


    static ZooKeeper zk = null;
    static final Object mutex = new Object();

    String root;


    SyncPrimitive(String address)
            throws KeeperException, IOException {

        if(address==null){
            address="localhost";
        }
        if (zk == null) {
            System.out.println("Starting ZK:");
            zk = new ZooKeeper(address, 3000, this);
            System.out.println("Finished starting ZK: " + zk);
        }
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
