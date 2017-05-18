package io.vertx.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by yuanchongjie on 2017/5/11.
 */
public class WatcherImp implements Watcher {

    static ZooKeeper zk;
    String hostPort;

    WatcherImp(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() {
        try {
            if (hostPort == null) {
                hostPort = "127.0.0.1:2181";
            }
            zk = new ZooKeeper(hostPort, 15000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void stopZK() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static String serverId = Integer.toHexString(new Random().nextInt());
    static boolean isLeader = false;

    static boolean checkMaster() {
        while (true) {
            try {
                Stat stat = new Stat();
                byte data[] = zk.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException.NoNodeException e) {
// no master, so try create again
                return false;
            } catch (KeeperException.ConnectionLossException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    static void runForMaster() {
        try {
            zk.create("/master",
                    serverId.getBytes(),
                    OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        public void processResult(int rc, String path, Object ctx, byte[] data,
                                  Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case NONODE:
                    runForMaster();
                    return;
            }
        }
    };

    static AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                default:
                    isLeader = false;
            }
            System.out.println("I'm " + (isLeader ? "" : "not ") +
                    "the leader");
        }
    };

    static void runForMaster2() {
        zk.create("/master", serverId.getBytes(), OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, masterCreateCallback, null);
    }


    public void process(WatchedEvent e) {
        System.out.println(e);
    }


    public void bootstrap() {
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    void createParent(String path, byte[] data) {
        zk.create(path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                createParentCallback,
                data);
    }

    AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    System.out.println("Parent created");
                    break;
                case NODEEXISTS:
                    System.out.println("Parent already registered: " + path);
                    break;
                default:
                    System.out.println("Something went wrong: ");
                    KeeperException.create(KeeperException.Code.get(rc), path).printStackTrace();
            }
        }
    };

    void register() {
        zk.create("/workers/worker-" + serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createWorkerCallback, null);
    }

    AsyncCallback.StringCallback createWorkerCallback = new AsyncCallback.StringCallback() {
        public void processResult(int rc, String path, Object ctx,
                                  String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    System.out.println("Registered successfully: " + serverId);
                    break;
                case NODEEXISTS:
                    System.out.println("  already registered: " + serverId);
                    break;
                default:
                    System.out.println("Something went wrong: ");
                    KeeperException.create(KeeperException.Code.get(rc), path).printStackTrace();
            }
        }
    };

    public static void main(String args[])
            throws Exception {
        WatcherImp watcherImp = new WatcherImp(null);
        watcherImp.startZK();
        watcherImp.bootstrap();
        watcherImp.register();
        Thread.sleep(30000);

            /*watcherImp.runForMaster2();
            zk.getData("/master",true,masterCheckCallback,null);*/
        //zk.getData();

        /*if(watcherImp.checkMaster()){
            System.out.println("i am leader");
        }*/

// wait for a bit
       // watcherImp.stopZK();


    }

}
