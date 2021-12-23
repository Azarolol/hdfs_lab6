package hdfs.lab4.azarolol;

import akka.actor.ActorRef;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperWatcher implements Watcher {
    private final ActorRef storage;
    private final ZooKeeper zoo;

    public ZookeeperWatcher(ActorRef storage, ZooKeeper zoo) {
        this.storage = storage;
        this.zoo = zoo;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        zoo.getChildren(SERVER_PATH, this);

    }
}
