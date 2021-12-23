package hdfs.lab4.azarolol;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.ZooKeeper;

public class AnonymaizerApp {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storage = system.actorOf(Props.create(ConfigStorageActor.class));
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        ZooKeeper zoo = new ZooKeeper(args[INDEX_OF_])

    }
}
