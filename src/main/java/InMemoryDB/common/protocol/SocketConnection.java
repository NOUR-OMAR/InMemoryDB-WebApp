package InMemoryDB.common.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.*;

public class SocketConnection {
    @Setter
    @Getter
    private static ObjectInputStream socketInput;
    @Setter
    @Getter
    private static Socket socket;
    @Setter
    @Getter
    private static boolean serverAlive = true;
    @Setter
    @Getter
    private static boolean close = false;

    public static String SendRequestReceiveResponse(String command) {
        if (isServerAlive()) {
            String response = "";
            OutputStream outputStream;
            try (Socket socket = new Socket(SERVER_ADDRESS, PORT_NUMBER)) {
                outputStream = socket.getOutputStream();
                PrintWriter socketWriter = new PrintWriter(outputStream, true);// true=> auto flush buffers
                socketWriter.println(command);
                Scanner socketReader = new Scanner(socket.getInputStream());
                display("Client's request: " + command);
                if (!isClose()) {
                    response = socketReader.nextLine();
                    display("Server's response: " + response);
                } else {
                    closeAll(outputStream, socketWriter, socketReader);
                }
            } catch (IOException ioException) {
                display("Client Exception: " + ioException.getLocalizedMessage());
            } catch (NullPointerException nullPointerException) {
                setServerAlive(false);
                display(SERVER_NOT_RESPONDING);
                return "";
            } catch (NoSuchElementException noSuchElementException) {
                noSuchElementException.printStackTrace();
            }
            setServerAlive(true);
            return response;
        } else {

            return "";
        }

    }


    private static void closeAll(OutputStream outputStream, PrintWriter socketWriter, Scanner socketReader) throws IOException {
        socketWriter.close();
        socketReader.close();
        //   socket.close();
        outputStream.close();
    }


}
