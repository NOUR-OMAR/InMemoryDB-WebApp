package InMemoryDB.server.database.record;

public interface RecordHandler {
    Object parseRecord(String Record) throws IllegalArgumentException;

}
