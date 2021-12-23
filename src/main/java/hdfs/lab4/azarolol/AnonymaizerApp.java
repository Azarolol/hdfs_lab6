package hdfs.lab4.azarolol;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class AnonymaizerApp {
    private static final String ACTOR_SYSTEM_NAME = "AnonymizerActors";
    private static final int INDEX_OF_SERVER = 0;
    private static final int ZOOKEEPER_TIMEOUT = 3000;
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storage = system.actorOf(Props.create(ConfigStorageActor.class));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        ZooKeeper zoo = new ZooKeeper(args[INDEX_OF_SERVER], ZOOKEEPER_TIMEOUT, null);


    }
}
