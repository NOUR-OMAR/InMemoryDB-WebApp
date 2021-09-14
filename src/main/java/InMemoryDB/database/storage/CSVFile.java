package InMemoryDB.database.storage;

import InMemoryDB.database.Database;
import InMemoryDB.database.record.DepartmentRecord;
import InMemoryDB.database.record.EmployeeRecord;
import InMemoryDB.database.record.RecordHandler;
import InMemoryDB.database.record.UsersRecord;
import InMemoryDB.database.storage.log.TransactionLog;
import InMemoryDB.database.storage.log.TransactionLogger;
import InMemoryDB.model.Department;
import InMemoryDB.model.Employee;
import InMemoryDB.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

import static InMemoryDB.utils.Display.display;


public class CSVFile extends FileIOHandler implements FileHandler {

    @Setter
    @Getter
    private static long employeesCSVRowCount = 0;
    @Setter
    @Getter
    private static long departmentsCSVRowCount = 0;

    @Setter
    @Getter
    private static long usersCSVRowCount = 0;

    public TransactionLogger transactionLog = new TransactionLog();

    RecordHandler employeeRecordHandler = new EmployeeRecord();
    RecordHandler departmentRecordHandler = new DepartmentRecord();
    RecordHandler userRecordHandler = new UsersRecord();

    public CSVFile() throws IOException {
    }


    public void initialize() throws IOException {
        display("Loading data from file " + FilesPaths.USERS_FILE_PATH.getPath());
        loadData(FilesPaths.USERS_FILE_PATH.getPath());
        display("Loading data from file " + FilesPaths.DEPARTMENTS_CSV_PATH.getPath());
        loadData(FilesPaths.DEPARTMENTS_CSV_PATH.getPath());
        display("Loading data from file " + FilesPaths.EMPLOYEES_CSV_PATH.getPath());
        loadData(FilesPaths.EMPLOYEES_CSV_PATH.getPath());

    }


    private void loadData(String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();

        if (filePath.equalsIgnoreCase(FilesPaths.EMPLOYEES_CSV_PATH.getPath())) {

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

                readEmployeeBuffer(bufferedReader);
            } catch (FileNotFoundException fileNotFoundException) {
                display("The provided csv file was not found.A new empty server.database file will be created");
                try (FileWriter fileWriter = new FileWriter(file, true)) {
                    display("A new empty server.database file will be created" + fileWriter.toString());
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (filePath.equalsIgnoreCase(FilesPaths.DEPARTMENTS_CSV_PATH.getPath())) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

                readDepartmentsBuffer(bufferedReader);
            } catch (FileNotFoundException fileNotFoundException) {
                display("The provided csv file was not found.A new empty server.database file will be created");
                try (FileWriter fileWriter = new FileWriter(file, true)) {
                    display("A new empty server.database file will be created" + fileWriter.toString());
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (filePath.equalsIgnoreCase(FilesPaths.USERS_FILE_PATH.getPath())) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                readUsersBuffer(bufferedReader);

            } catch (FileNotFoundException fileNotFoundException) {
                display("The provided csv file was not found.A new empty file will be created");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

    }


    @Override
    public void write(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (fileName.equalsIgnoreCase(FilesPaths.EMPLOYEES_CSV_PATH.getPath())) {
            buildEmployeeRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(FilesPaths.DEPARTMENTS_CSV_PATH.getPath())) {
            buildDepartmentRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(FilesPaths.USERS_FILE_PATH.getPath())) {
            buildUsersRecordString(stringBuilder);
        }
        super.tryWritingToFile(stringBuilder, fileName);

    }

    private void readEmployeeBuffer(BufferedReader bufferedReader) throws IOException {
        String record;

        while ((record = bufferedReader.readLine()) != null) {

            setEmployeesCSVRowCount(getEmployeesCSVRowCount() + 1);


            Employee employee = (Employee) employeeRecordHandler.parseRecord(record.replaceAll("\\s+", ""));
            if (employee != null) {
                Database.getAllEmployees().put(employee.getId(), employee);
            }


        }
    }

    private void readDepartmentsBuffer(BufferedReader bufferedReader) throws IOException {
        String record;

        while ((record = bufferedReader.readLine()) != null) {

            setDepartmentsCSVRowCount(getDepartmentsCSVRowCount() + 1);


            Department department = (Department) departmentRecordHandler.parseRecord(record.replaceAll("\\s+", ""));
            if (department != null) {
                Database.getAllDepartments().put(department.getId(), department);

            }


        }
    }

    private void readUsersBuffer(BufferedReader bufferedReader) throws IOException {
        String row;

        while ((row = bufferedReader.readLine()) != null) {

            User user = (User) userRecordHandler.parseRecord(row.replaceAll("\\s+", ""));
            if (user != null) {

                Database.getAllUsers().put(user.getUsername(), user);
            }
        }
        display("Users are loaded");
    }


}
