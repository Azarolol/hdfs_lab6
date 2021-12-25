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
    private static final String LOG_FORMAT_STRING = "Server %d: %s";

    public ZookeeperWatcher(ActorRef storage, ZooKeeper zoo, LoggingAdapter log) throws InterruptedException, KeeperException {
        this.storage = storage;
        this.zoo = zoo;
        this.log = log;

        update();
    }

    public void update() {
        List<String> servers = new ArrayList<String>();
        try {
            for (String s : zoo.getChildren(SERVERS_PATH, this)) {
                servers.add(new String(zoo.getData(SERVERS_PATH + "/" + s, false, null)));
            }
        } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
        }
        for (int i = 0; i < servers.size(); i++) {
            log.info(String.format(LOG_FORMAT_STRING, i, servers.get(i)));
        }
        storage.tell(new ServersListMessage(servers), ActorRef.noSender());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        update();
    }
}
