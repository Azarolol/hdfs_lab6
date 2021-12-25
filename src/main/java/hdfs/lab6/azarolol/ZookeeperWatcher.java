package hdfs.lab6.azarolol;

import akka.actor.ActorRef;
import akka.event.LoggingAdapter;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.*;

public class ZookeeperWatcher implements Watcher {
    private final ActorRef storage;
    private final ZooKeeper zoo;
    private final LoggingAdapter log;
    private static final String SERVERS_PATH = "/servers";
    private static final String LOG_FORMAT_STRING = "Server added: %s";

    public ZookeeperWatcher(ActorRef storage, ZooKeeper zoo, LoggingAdapter log) throws InterruptedException, KeeperException {
        this.storage = storage;
        this.zoo = zoo;
        this.log = log;

        List<String> servers = new ArrayList<String>();
        servers = zoo.getChildren(SERVERS_PATH, this);

        for (String s : servers) {
            servers.add(new String(zoo.getData(SERVERS_PATH + "/" + s, false, null)));
        }
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
                log.info(String.format(LOG_FORMAT_STRING, s));
                servers.add(new String(zoo.getData(SERVERS_PATH + "/" + s, false, null)));
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.tell(new ServersListMessage(servers), ActorRef.noSender());
    }
}
