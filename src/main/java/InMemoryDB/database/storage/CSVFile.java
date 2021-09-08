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

import java.io.*;

import static InMemoryDB.utils.Constant.*;
import static InMemoryDB.utils.Constant.Display.display;

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

    @Override
    public void initialize() throws IOException {
        display("Loading data from file " + USERS_FILE_PATH);
        loadData(USERS_FILE_PATH);
        display("Loading data from file " + DEPARTMENTS_CSV_PATH);
        loadData(DEPARTMENTS_CSV_PATH);
        display("Loading data from file " + EMPLOYEES_CSV_PATH);
        loadData(EMPLOYEES_CSV_PATH);

    }


    private void loadData(String filePath) throws IOException {

        if (filePath.equalsIgnoreCase(EMPLOYEES_CSV_PATH)) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

                readEmployeeBuffer(bufferedReader);
            } catch (FileNotFoundException fileNotFoundException) {
                display("The provided csv file was not found.A new empty server.database file will be created");
                try (FileWriter fileWriter = new FileWriter(filePath, true)) {
                    display("A new empty server.database file will be created" + fileWriter.toString());
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (filePath.equalsIgnoreCase(DEPARTMENTS_CSV_PATH)) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

                readDepartmentsBuffer(bufferedReader);
            } catch (FileNotFoundException fileNotFoundException) {
                display("The provided csv file was not found.A new empty server.database file will be created");
                try (FileWriter fileWriter = new FileWriter(filePath, true)) {
                    display("A new empty server.database file will be created" + fileWriter.toString());
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if (filePath.equalsIgnoreCase(USERS_FILE_PATH)) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                readUsersBuffer(bufferedReader);

            } catch (FileNotFoundException fileNotFoundException) {
                display("The provided csv file was not found.A new empty file will be created");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

    }


    @Override
    public void write(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        if (fileName.equalsIgnoreCase(EMPLOYEES_CSV_PATH)) {
            buildEmployeeRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(DEPARTMENTS_CSV_PATH)) {
            buildDepartmentRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(USERS_FILE_PATH)) {
            buildUsersRecordString(stringBuilder);
        }
        tryWritingToFile(stringBuilder, fileName);

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
