package InMemoryDB.server.handler;

import java.net.Socket;

public class ClientHandlerBuilder {
    private Socket clientSocket;

    public ClientHandlerBuilder setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        return this;
    }

    public ClientHandler createClientHandler() {
        return new ClientHandler(clientSocket);
    }
}