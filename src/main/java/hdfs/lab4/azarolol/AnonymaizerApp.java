package hdfs.lab4.azarolol;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
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
    private static final String LOCALHOST = "localhost";
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storage = system.actorOf(Props.create(ConfigStorageActor.class));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final String port = args[INDEX_OF_SERVER];
        final String url = LOCALHOST + ":" + port;
        ZooKeeper zoo = new ZooKeeper(url, ZOOKEEPER_TIMEOUT, null);
        ZookeeperWatcher watcher = new ZookeeperWatcher(storage, zoo);

        try {
            zoo.create(SERVERS_PATH,
                    url.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = new HTTPServer(http, storage, materializer.logger()).createRoute().flow(system, materializer);
        http.bindAndHandle(routeFlow, ConnectHttp.toHost(LOCALHOST, Integer.parseInt(port)), materializer);
    }
}
