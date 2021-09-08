package InMemoryDB.utils;


import lombok.Data;

import java.io.File;

public final class Constant {

    static File employeesCSVFile = new File("src/main/resources/employees.csv");
    public static final String EMPLOYEES_CSV_PATH = employeesCSVFile.getAbsolutePath();
    static File departmentsCSVFile = new File("src/main/resources/departments.csv");
    public static final String DEPARTMENTS_CSV_PATH = departmentsCSVFile.getAbsolutePath();
    static File usersCSVFile = new File("src/main/resources/Users.csv");
    public static final String USERS_FILE_PATH = usersCSVFile.getAbsolutePath();
    static File employeesLoggerFile = new File("src/main/resources/employees_logger.txt");
    public static final String EMPLOYEES_LOGGER_FILE = employeesLoggerFile.getAbsolutePath();
    static File departmentsLoggerFile = new File("src/main/resources/departments_logger.txt");
    public static final String DEPARTMENTS_LOGGER_FILE = departmentsLoggerFile.getAbsolutePath();
    static File usersLoggerFile = new File("src/main/resources/users_logger.txt");
    public static final String USERS_LOGGER_FILE = usersLoggerFile.getAbsolutePath();

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
