package InMemoryDB.utils;


import lombok.Data;

public final class Constant {
    public static final String SERVER_NOT_RESPONDING = "server not responding";
    public static final String SERVER_ADDRESS = "localhost";
    public static final String CSV_PATH = "C:\\Users\\Dell\\Desktop\\InMemoryDB spring\\InMemoryDB\\employees.csv";
    public static final String USERS_FILE_PATH = "C:\\Users\\Dell\\Desktop\\InMemoryDB spring\\InMemoryDB\\Users.csv";
    public static final int PORT_NUMBER = 2525;
    public static final String LOGGER_FILE = "logger.txt";
    public static final int MAX_SIZE = 1000;
    public static final int RECORD_LENGTH = 3;
    public static final int ROW_LENGTH = 2;
    public static final int MIN_SALARY = 1000;
    public static final int MAX_SALARY = 10000;
    public static final int DELAY_TIME = 5;
    public static final String ERROR_MESSAGE = "Missing parameters";
    public static final String NULL = "null";
    public static final String SALARY_ERROR_MESSAGE = "\nError: salary must be between 1000 and 10000.";

    public static final String HELP_MESSAGE = """

            List of operations: 	
                Create id name salary : Inserts / creates a new record in the database.
            	Read id : Reads the table records based on the primary key(id) within the input parameter. \s
            	Update id name salary : Updates the content of the table based on the specified primary key(id) for a record.\s
            	Delete id : Deletes a specified row in the table based on the specified primary key(id).\s
             	FilterSalary operator salary : selects all records with salary based on the given operator,  \s
            		operator : "less than", "greater than", "equals".\s
             	FilterName beginningOfName : selects all records with given name.\s
             	SelectAll  : selects all records in database.\s
             	Help   : shows this help box. \s
             	Exit  : exit and save CSV file.\s
            """;


    public final static class OperationCommand {
        public static final String CREATE = "Create";
        public static final String UPDATE = "Update";
        public static final String READ = "Read";
        public static final String DELETE = "Delete";
        public static final String HELP = "Help";
        public static final String CLOSE = "Close";
        public static final String FILTER_NAME = "FilterName";
        public static final String FILTER_SALARY = "FilterSalary";
        public static final String SELECT_ALL = "SelectAll";

    }


    public final static class SalaryCondition {
        public static final String LESS_THAN = "l";
        public static final String GREATER_THAN = "g";
        public static final String EQUALS = "e";
    }


    @Data
    public final static class Display {
        public static void display(String message) {
            System.out.println(message);
        }

    }

}
