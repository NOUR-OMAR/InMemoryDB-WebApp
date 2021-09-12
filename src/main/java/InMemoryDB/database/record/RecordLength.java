package InMemoryDB.database.record;

public enum RecordLength {


    EMPLOYEES_RECORD_LENGTH(4),
    DEPARTMENT_RECORD_LENGTH(3),
    USERS_RECORD_LENGTH(4);
    private int length;

    RecordLength(int length) {
        this.length = length;
    }
    public int getRecordLength() {
        return length;
    }
}

