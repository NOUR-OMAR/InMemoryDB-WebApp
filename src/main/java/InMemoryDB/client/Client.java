package InMemoryDB.client;

import InMemoryDB.common.protocol.SocketConnection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static InMemoryDB.utils.Constant.Display.display;

@Data
public class Client {

    @Setter
    @Getter
    private static ObjectInputStream socketInput;
    private ObjectOutputStream socketOutput;
    private String username;

    public Client(String username) throws IOException {
        this.username = username;
    }

    public boolean start() {
        try {
            display("Client :" + username + " connected");
        } catch (Exception exception) {
            display("Client message: Error connecting to server:" + exception);
            return false;
        }

        return true;

    }

    protected void disconnect() {
        try {
            if (!isNullSocketInput()) getSocketInput().close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            if (!isNullSocketOutput()) getSocketOutput().close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            if (!isNullSocket()) SocketConnection.getSocket().close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private boolean isNullSocketInput() {
        return getSocketInput() == null;
    }

    private boolean isNullSocketOutput() {
        return getSocketOutput() == null;
    }

    private boolean isNullSocket() {
        return SocketConnection.getSocket() == null;
    }


}

