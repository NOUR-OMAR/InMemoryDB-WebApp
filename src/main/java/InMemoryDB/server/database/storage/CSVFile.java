package InMemoryDB.server.database.storage;

import InMemoryDB.client.model.Department;
import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.Database;
import InMemoryDB.server.database.record.DepartmentRecord;
import InMemoryDB.server.database.record.EmployeeRecord;
import InMemoryDB.server.database.record.RecordHandler;
import InMemoryDB.server.database.storage.log.TransactionLog;
import InMemoryDB.server.database.storage.log.TransactionLogger;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Map;

import static InMemoryDB.utils.Constant.DEPARTMENTS_CSV_PATH;
import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.EMPLOYEES_CSV_PATH;


public class CSVFile implements FileHandler {

    @Setter
    @Getter
    private static long employeesCSVRowCount = 0;

    @Setter
    @Getter
    private static long departmentsCSVRowCount = 0;
    public TransactionLogger transactionLog = new TransactionLog();
    RecordHandler employeeRecordHandler = new EmployeeRecord();
    RecordHandler departmentRecordHandler = new DepartmentRecord();

    public CSVFile() throws IOException {
    }

    @Override
    public void initialize() throws IOException {
        display("Loading data from file " + DEPARTMENTS_CSV_PATH);
        loadDepartmentsData();
        display("Loading data from file " + EMPLOYEES_CSV_PATH);
        loadEmployeeData();

    }


    private void loadEmployeeData() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(EMPLOYEES_CSV_PATH))) {

            readEmployeeBuffer(bufferedReader);
        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided csv file was not found.A new empty server.database file will be created");
            try (FileWriter fileWriter = new FileWriter(EMPLOYEES_CSV_PATH, true)) {
                display("A new empty server.database file will be created" + fileWriter.toString());
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    private void loadDepartmentsData() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DEPARTMENTS_CSV_PATH))) {

            readDepartmentsBuffer(bufferedReader);
        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided csv file was not found.A new empty server.database file will be created");
            try (FileWriter fileWriter = new FileWriter(DEPARTMENTS_CSV_PATH, true)) {
                display("A new empty server.database file will be created" + fileWriter.toString());
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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
                //  setEmployeeLRUCache(employee.getId(), employee);
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

    public void tryWritingToEmployeesFile(StringBuilder stringBuilder) {
        try (FileWriter fileWriter = new FileWriter(EMPLOYEES_CSV_PATH, false)) {
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

            display("Data saved to file: " + EMPLOYEES_CSV_PATH);
        } catch (IOException ignored) {
            display("Error: Saving the data is failed!");
        }
    }

    public void setEmployeeRecordStringBuilder(StringBuilder stringBuilder) {

        for (Integer integer : Database.getTableLRUCache().keySet()) {
            Database.getAllEmployees().put(integer, (Employee) Database.getTableLRUCache().get(integer));
        }
        // Database.getAllEmployees().putAll((Map<? extends Integer, ? extends Employee>)  Database.getTableLRUCache().values());
        for (Map.Entry<Integer, Employee> entry : Database.getAllEmployees().entrySet()) {
            Employee employee = entry.getValue();
            stringBuilder.append(toEmployeeRecord(employee));
        }
    }


    public String toEmployeeRecord(Employee employee) {
        return employee.getId() + ";" +
                employee.getName() + ";" +
                employee.getSalary() + ";" +
                employee.getDepartment().getId() + "\n";
    }

    public void setDepartmentRecordStringBuilder(StringBuilder stringBuilder) {

        for (Integer integer : Database.getTableLRUCache().keySet()) {
            Database.getAllDepartments().put(integer, (Department) Database.getTableLRUCache().get(integer));
        }
        //  Database.getAllDepartments().putAll((Map<? extends Integer, ? extends Department>)  Database.getTableLRUCache().values());
        for (Map.Entry<Integer, Department> entry : Database.getAllDepartments().entrySet()) {
            Department department = entry.getValue();
            stringBuilder.append(toDepartmentRecord(department));
        }
    }


    public String toDepartmentRecord(Department department) {
        return department.getId() + ";" +
                department.getName() + ";" +
                department.getLocation() + "\n";
    }


}
