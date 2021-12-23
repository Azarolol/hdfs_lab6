package hdfs.lab4.azarolol;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class AnonymaizerApp {
    private static final String ACTOR_SYSTEM_NAME = "AnonymizerActors";
    private static final int INDEX_OF_SERVER = 0;
    private static final int ZOOKEEPER_TIMEOUT = 3000;
    private static final String SERVERS_PATH = "/servers";
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storage = system.actorOf(Props.create(ConfigStorageActor.class));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final String port = args[INDEX_OF_SERVER];
        ZooKeeper zoo = new ZooKeeper(port, ZOOKEEPER_TIMEOUT, null);
        ZookeeperWatcher watcher = new ZookeeperWatcher(storage, zoo);

        final String url = "localhost:" + port;
        zoo.create(SERVERS_PATH,
                url.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        final Flow<HttpRequest, HttpResponse, NotUsed> 
    }
}
