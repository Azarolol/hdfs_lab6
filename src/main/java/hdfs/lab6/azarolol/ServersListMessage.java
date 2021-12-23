package hdfs.lab6.azarolol;

import java.util.List;

public class ServersListMessage {
    private final List<String> servers;

    public ServersListMessage(List<String> servers) {
        this.servers = servers;
    }

    public List<String> getServers() {
        return servers;
    }
}
