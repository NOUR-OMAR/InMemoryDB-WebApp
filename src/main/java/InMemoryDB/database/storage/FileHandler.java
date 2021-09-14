package InMemoryDB.database.storage;

import java.io.IOException;

public interface FileHandler {
    void write(String fileName) throws IOException;
}
