package InMemoryDB.server;

import InMemoryDB.server.database.storage.log.TransactionLog;

import java.io.IOException;

import static InMemoryDB.utils.Constant.PORT_NUMBER;


public class MainServer {
    static TransactionLog transactionLog;

    static {
        try {
            transactionLog = new TransactionLog();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception exception) {
            transactionLog.writeToCSV();
            exception.printStackTrace();

        }

    }

    public static void start() throws Exception {

        Server server = new ServerBuilder().setPort(PORT_NUMBER).createServer();
        server.start();

    }

}
