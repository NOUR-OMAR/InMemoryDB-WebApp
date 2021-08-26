package InMemoryDB.server.database.record;

import InMemoryDB.client.model.User;

public interface RecordHandler {
    User parseRecord(String Record) throws IllegalArgumentException;

}
