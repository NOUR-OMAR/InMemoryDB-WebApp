package InMemoryDB.server.database.storage;

import InMemoryDB.client.model.Department;
import InMemoryDB.client.model.Employee;
import InMemoryDB.client.model.User;
import InMemoryDB.server.database.Database;
import InMemoryDB.server.database.record.DepartmentRecord;
import InMemoryDB.server.database.record.EmployeeRecord;
import InMemoryDB.server.database.record.RecordHandler;
import InMemoryDB.server.database.record.UsersRecord;
import InMemoryDB.server.database.storage.log.TransactionLog;
import InMemoryDB.server.database.storage.log.TransactionLogger;
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

    /*
        private void readEmployeeBuffer(BufferedReader bufferedReader) throws IOException {
            String record;
            int iteration = 0;
            while ((record = bufferedReader.readLine()) != null) {
                if (iteration == 0) {
                    iteration++;
                    continue;
                }
                setEmployeesCSVRowCount(getEmployeesCSVRowCount() + 1);


                Employee employee = (Employee) employeeRecordHandler.parseRecord(record.replaceAll("\\s+", ""));
                if (employee != null) {
                    Database.getAllEmployees().put(employee.getId(), employee);
                }


            }
        }

        private void readDepartmentsBuffer(BufferedReader bufferedReader) throws IOException {
            String record;
            int iteration = 0;
            while ((record = bufferedReader.readLine()) != null) {
                if (iteration == 0) {
                    iteration++;
                    continue;
                }
                setDepartmentsCSVRowCount(getDepartmentsCSVRowCount() + 1);


                Department department = (Department) departmentRecordHandler.parseRecord(record.replaceAll("\\s+", ""));
                if (department != null) {
                    Database.getAllDepartments().put(department.getId(), department);
                    //  setEmployeeLRUCache(employee.getId(), employee);
                }


            }
        }

        public void tryWritingToFile(StringBuilder stringBuilder,String filePath) {
            try (FileWriter fileWriter = new FileWriter(filePath, false)) {
                fileWriter.write(stringBuilder.toString());
                fileWriter.flush();
                fileWriter.close();

                display("Data saved to file: " + filePath);
            } catch (IOException ignored) {
                display("Error: Saving the data is failed!");
            }
        }


        public static String toDepartmentRecord(Department department) {
            return department.getId() + ";" +
                    department.getName() + ";" +
                    department.getLocation() + "\n";
        }

        public static String toEmployeeRecord(Employee employee) {
            return employee.getId() + ";" +
                    employee.getName() + ";" +
                    employee.getSalary() + ";" +
                    employee.getDepartment().getId() + "\n";
        }



        private static void buildEmployeeRecordString(StringBuilder stringBuilder) {

            for (Integer integer : Database.getTableLRUCache().snapshot().keySet()) {
                if (Database.getTableLRUCache().snapshot() instanceof Department)

                    Database.getAllEmployees().put(integer, (Employee) Objects.requireNonNull(Database.getTableLRUCache().get(integer)));
            }

            for (Map.Entry<Integer, Employee> entry : Database.getAllEmployees().entrySet()) {
                Employee employee = entry.getValue();
                stringBuilder.append(toEmployeeRecord(employee));
            }
        }



        private static void buildDepartmentRecordString(StringBuilder stringBuilder) {

            for (Integer integer : Database.getTableLRUCache().snapshot().keySet()) {
                if (Database.getTableLRUCache().snapshot() instanceof Department)

                    Database.getAllDepartments().put(integer, (Department) Objects.requireNonNull(Database.getTableLRUCache().get(integer)));
            }

            for (Map.Entry<Integer, Department> entry : Database.getAllDepartments().entrySet()) {
                Department department = entry.getValue();
                stringBuilder.append(toDepartmentRecord(department));
            }
        }



    */
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
        int iteration = 0;
        while ((record = bufferedReader.readLine()) != null) {
            if (iteration == 0) {
                iteration++;
                continue;
            }
            setEmployeesCSVRowCount(getEmployeesCSVRowCount() + 1);


            Employee employee = (Employee) employeeRecordHandler.parseRecord(record.replaceAll("\\s+", ""));
            if (employee != null) {
                Database.getAllEmployees().put(employee.getId(), employee);
            }


        }
    }

    private void readDepartmentsBuffer(BufferedReader bufferedReader) throws IOException {
        String record;
        int iteration = 0;
        while ((record = bufferedReader.readLine()) != null) {
            if (iteration == 0) {
                iteration++;
                continue;
            }
            setDepartmentsCSVRowCount(getDepartmentsCSVRowCount() + 1);


            Department department = (Department) departmentRecordHandler.parseRecord(record.replaceAll("\\s+", ""));
            if (department != null) {
                Database.getAllDepartments().put(department.getId(), department);
                //  setEmployeeLRUCache(employee.getId(), employee);
            }


        }
    }

    private void readUsersBuffer(BufferedReader bufferedReader) throws IOException {
        String row;
        int iteration = 0;
        while ((row = bufferedReader.readLine()) != null) {
            if (iteration == 0) {
                iteration++;
                continue;
            }
            User user = (User) userRecordHandler.parseRecord(row);
            if (user != null) {

                Database.getAllUsers().put(user.getUsername(), user);
            }
        }
        display("Users are loaded");
    }


}
