package hdfs.lab4.azarolol;

import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigStorageActor extends AbstractActor {
    private List<String> servers = new ArrayList<>();
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServersListMessage.class,
                        message -> )
                .match(GetRandoServerMessage.class,
                        message -> sender().tell(servers.get(new Random().nextInt(servers.size()))))
    }
}
