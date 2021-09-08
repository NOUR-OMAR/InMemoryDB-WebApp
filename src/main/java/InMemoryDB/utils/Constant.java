package InMemoryDB.utils;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@ConfigurationProperties(prefix = "dir.local")
@Configuration
public class Constant {

   /* static File employeesCSVFile = new File("employees.csv");
    public static final String EMPLOYEES_CSV_PATH = employeesCSVFile.getAbsolutePath();
    static File departmentsCSVFile = new File("departments.csv");
    public static final String DEPARTMENTS_CSV_PATH = departmentsCSVFile.getAbsolutePath();
    static File usersCSVFile = new File("Users.csv");
    public static final String USERS_FILE_PATH = usersCSVFile.getAbsolutePath();
    static File employeesLoggerFile = new File("employees_logger.txt");
    public static final String EMPLOYEES_LOGGER_FILE = employeesLoggerFile.getAbsolutePath();
    static File departmentsLoggerFile = new File("departments_logger.txt");
    public static final String DEPARTMENTS_LOGGER_FILE = departmentsLoggerFile.getAbsolutePath();
    static File usersLoggerFile = new File("users_logger.txt");
    public static final String USERS_LOGGER_FILE = usersLoggerFile.getAbsolutePath();*/


    //static File employeesCSVFile = new File("employees.csv");

    @Value("${dir.local.employeesCSV}")
    public static final String EMPLOYEES_CSV_PATH = "employees.csv";
    //static File departmentsCSVFile = new File("departments.csv");
    @Value("${dir.local.departmentsCSV}")
    public static final String DEPARTMENTS_CSV_PATH = "departments.csv";
    //static File usersCSVFile = new File("Users.csv");
    @Value("${dir.local.UsersCSV}")
    public static final String USERS_FILE_PATH = "Users.csv";
    @Value("${dir.local.employeesLogger}")
    public static final String EMPLOYEES_LOGGER_FILE = "employees_logger.txt";
    @Value("${dir.local.departmentsLogger}")

    public static final String DEPARTMENTS_LOGGER_FILE = "departments_logger.txt";
    @Value("${dir.local.usersLogger}")

    public static final String USERS_LOGGER_FILE = "users_logger.txt";
    static File employeesLoggerFile = new File("employees_logger.txt");
    static File departmentsLoggerFile = new File("departments_logger.txt");
    static File usersLoggerFile = new File("users_logger.txt");

    public static final int CACHE_MAX_SIZE = 1000;
    public static final int EMPLOYEES_RECORD_LENGTH = 4;
    public static final int DEPARTMENT_RECORD_LENGTH = 3;
    public static final int USERS_RECORD_LENGTH = 4;


    @Data
    public static class Display {
        public static void display(String message) {
            System.out.println(message);
        }

    }

}


