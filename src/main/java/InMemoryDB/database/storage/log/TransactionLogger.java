package InMemoryDB.database.storage.log;

import java.io.IOException;

public interface TransactionLogger {
    void write(String fileName) throws IOException;

    void writeToCSV();
}
