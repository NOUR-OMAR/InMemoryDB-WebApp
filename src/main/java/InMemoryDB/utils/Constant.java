package InMemoryDB.utils;


import lombok.Data;

//@ConfigurationProperties(prefix = "dir.local")
//@Configuration
//@Component
public final class Constant {


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
   /* @Autowired
    Environment environment;*/
/*
    @Value("${dir.local.employeesCSV}")
    public static String EMPLOYEES_CSV_PATH ;//= "employees.csv";
    //static File departmentsCSVFile = new File("departments.csv");
    @Value("${dir.local.departmentsCSV}")
    public static  String DEPARTMENTS_CSV_PATH;
    //static File usersCSVFile = new File("Users.csv");
    @Value("${dir.local.UsersCSV}")
    public static  String USERS_FILE_PATH ;
    @Value("${dir.local.employeesLogger}")
    public static  String EMPLOYEES_LOGGER_FILE ;
    @Value("${dir.local.departmentsLogger}")

    public static  String DEPARTMENTS_LOGGER_FILE;
    @Value("${dir.local.usersLogger}")

    public static  String USERS_LOGGER_FILE ;
*/


    public static final String DEPARTMENTS_CSV_PATH = "departments.csv";
    public static final String EMPLOYEES_CSV_PATH = "employees.csv";
    public static final String USERS_FILE_PATH = "Users.csv";
    public static final String DEPARTMENTS_LOGGER_FILE = "departments_logger.txt";
    public static final String EMPLOYEES_LOGGER_FILE = "employees_logger.txt";
    public static final String USERS_LOGGER_FILE = "users_logger.txt";


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


