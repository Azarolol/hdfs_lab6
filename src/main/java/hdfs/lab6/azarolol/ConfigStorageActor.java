package hdfs.lab6.azarolol;

import akka.actor.AbstractActor;
import akka.actor.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigStorageActor extends AbstractActor {
    private List<String> servers = new ArrayList<>();
    private final Random randomizer = new Random();
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServersListMessage.class,
                        message -> servers = message.getServers())
                .match(GetRandomServerMessage.class,
                        message -> sender().tell(servers.get(randomizer.nextInt(servers.size())), Actor.noSender()))
                .build();
    }
}
