package InMemoryDB.database.storage.log;

import InMemoryDB.database.storage.FileHandler;

public interface TransactionLogger extends FileHandler {

    void writeToCSV();
}
