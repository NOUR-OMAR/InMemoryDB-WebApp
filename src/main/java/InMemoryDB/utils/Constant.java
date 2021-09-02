package InMemoryDB.utils;


import lombok.Data;

public final class Constant {

    public static final String EMPLOYEES_CSV_PATH = "employees.csv";
    public static final String DEPARTMENTS_CSV_PATH = "departments.csv";
    public static final String USERS_FILE_PATH = "Users.csv";
    public static final String EMPLOYEES_LOGGER_FILE = "employees_logger.txt";
    public static final String DEPARTMENTS_LOGGER_FILE = "departments_logger.txt";
    public static final String USERS_LOGGER_FILE = "users_logger.txt";

    public static final int CACHE_MAX_SIZE = 1000;
    public static final int EMPLOYEES_RECORD_LENGTH = 4;
    public static final int DEPARTMENT_RECORD_LENGTH = 3;
    public static final int USERS_RECORD_LENGTH = 4;






    @Data
    public final static class Display {
        public static void display(String message) {
            System.out.println(message);
        }

    }

}
