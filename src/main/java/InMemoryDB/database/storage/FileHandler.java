package InMemoryDB.database.storage;

import java.io.IOException;

public interface FileHandler {
    void initialize() throws IOException;

    void write(String fileName) throws IOException;
}
