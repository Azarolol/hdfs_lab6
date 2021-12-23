package hdfs.lab6.azarolol;

import akka.actor.ActorRef;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.*;

public class ZookeeperWatcher implements Watcher {
    private final ActorRef storage;
    private final ZooKeeper zoo;
    private static final String SERVERS_PATH = "/servers";

    public ZookeeperWatcher(ActorRef storage, ZooKeeper zoo) {
        this.storage = storage;
        this.zoo = zoo;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        List<String> servers = new ArrayList<String>();
        try {
            servers = zoo.getChildren(SERVERS_PATH, this);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        for (String s : servers) {
            try {
                servers.add(new String(zoo.getData(SERVERS_PATH + "/" + s, false, null)));
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.tell(new ServersListMessage(servers), ActorRef.noSender());
    }
}
