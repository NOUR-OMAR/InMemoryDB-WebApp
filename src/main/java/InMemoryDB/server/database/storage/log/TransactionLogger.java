package InMemoryDB.server.database.storage.log;

public interface TransactionLogger {
    void write(String fileName);

    void writeToCSV();
}
