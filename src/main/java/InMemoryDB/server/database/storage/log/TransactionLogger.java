package InMemoryDB.server.database.storage.log;

public interface TransactionLogger {
    void write();

    void writeToCSV();
}
