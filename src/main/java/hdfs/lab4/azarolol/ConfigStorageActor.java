package hdfs.lab4.azarolol;

import akka.actor.AbstractActor;
import akka.actor.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigStorageActor extends AbstractActor {
    private final List<String> servers = new ArrayList<>();
    private final Random randomizer = new Random();
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServersListMessage.class,
                        message -> )
                .match(GetRandoServerMessage.class,
                        message -> sender().tell(servers.get(randomizer.nextInt(servers.size())), Actor.noSender()))
                .build();
    }
}
