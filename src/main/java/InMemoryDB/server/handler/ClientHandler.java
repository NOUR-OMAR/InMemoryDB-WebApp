package InMemoryDB.server.handler;

import InMemoryDB.common.operation.AbstractOperation;
import InMemoryDB.common.operation.OperationFactory;
import InMemoryDB.server.database.Database;
import lombok.Data;

import java.io.*;
import java.net.Socket;

import static InMemoryDB.common.operation.OperationTypes.Close;
import static InMemoryDB.utils.Constant.Display.display;

@Data
public class ClientHandler implements Runnable {


    private final OperationFactory operationFactory = new OperationFactory();
    private BufferedReader socketReader;
    private PrintWriter socketWriter;
    private Socket socket;


    public ClientHandler(Socket clientSocket) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            this.setSocketReader(new BufferedReader(inputStreamReader));
            OutputStream outputStream = clientSocket.getOutputStream();
            this.setSocketWriter(new PrintWriter(outputStream, true)); // true => auto flush socket buffer
            this.setSocket(clientSocket);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void run() {
        String command;
        try {
            while ((command = getSocketReader().readLine()) != null) {
                long start = System.currentTimeMillis();
                display("Server side : in (ClientHandler):  Read command from client :" + command);
                AbstractOperation employeeOperation = getOperationFactory().getOperation(command);
                if (!command.contains(Close.toString())) {
                    employeeOperation.start();
                    getSocketWriter().println(employeeOperation.runOperation());
                    long end = System.currentTimeMillis();
                    display("Elapsed Time in milli seconds: " + (end - start));
                    Database.getDatabase().displayCache();

                } else {
                    employeeOperation.start();
                    display("Server side : in (ClientHandler):Client is disconnected");
                    break;
                }
            }
            getSocket().close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}
