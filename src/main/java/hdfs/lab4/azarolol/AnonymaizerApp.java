package hdfs.lab4.azarolol;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class AnonymaizerApp {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        ActorRef storage = system.actorOf(Props.create(ConfigStorageActor.class));
        
    }
}
