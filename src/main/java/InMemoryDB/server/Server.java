package InMemoryDB.server;

import InMemoryDB.server.database.Database;
import InMemoryDB.server.handler.ClientHandlerBuilder;
import lombok.Data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static InMemoryDB.utils.Constant.Display.display;

@Data
public class Server {

    private final int port;
    private Database database;

    public Server(int port) {
        this.port = port;

    }


    public void start() throws IOException {
        setDatabase(Database.getDatabase());

        try {
            ServerSocket serverSocket = new ServerSocket(getPort());
            display("Server side: Server started.Server is Listening for connections on port " + getPort());

            while (true) {
                Socket socket = serverSocket.accept();

                display("Server side: Client has connected.");

                display("Server side : Port# of this server: " + socket.getLocalPort());

                Thread thread = new Thread(new ClientHandlerBuilder().setClientSocket(socket).createClientHandler());
                thread.start();

                display("Server side: ClientHandler started in thread for client ");
                display("Server side: Listening for connections");
            }
        } catch (IOException ioException) {
            display("Server: IOException: " + ioException);
        }

        display("Server: Server disconnected!");
    }


}
