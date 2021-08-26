package InMemoryDB.server;

public class ServerBuilder {
    private int port;

    public ServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public Server createServer() {
        return new Server(port);
    }
}