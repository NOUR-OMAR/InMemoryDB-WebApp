package InMemoryDB.database.storage;

public enum FilesPaths {

    DEPARTMENTS_CSV_PATH("departments.csv"),
    EMPLOYEES_CSV_PATH("employees.csv"),
    USERS_FILE_PATH("Users.csv"),
    DEPARTMENTS_LOGGER_FILE("departments_logger.txt"),
    EMPLOYEES_LOGGER_FILE("employees_logger.txt"),
    USERS_LOGGER_FILE("users_logger.txt");

    private String path;

    FilesPaths(String path) {
        this.path = path;
    }


    public String getPath() {
        return path;
    }
}
