package InMemoryDB.server.database.storage;

import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.Database;
import InMemoryDB.server.database.record.EmployeeRecord;
import InMemoryDB.server.database.record.RecordHandler;
import InMemoryDB.server.database.storage.log.TransactionLog;
import InMemoryDB.server.database.storage.log.TransactionLogger;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Map;

import static InMemoryDB.utils.Constant.CSV_PATH;
import static InMemoryDB.utils.Constant.Display.display;


public class CSVFile implements FileHandler {

    @Setter
    @Getter
    private static long rowCount = 0;
    public TransactionLogger transactionLog = new TransactionLog();
    RecordHandler recordHandler = new EmployeeRecord();

    public CSVFile() throws IOException {
    }

    @Override
    public void initialize() throws IOException {

        display("Loading data from file " + CSV_PATH);
        loadData();

    }


    private void loadData() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CSV_PATH))) {

            readBuffer(bufferedReader);
        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided csv file was not found.A new empty server.database file will be created");
            try (FileWriter fileWriter = new FileWriter(CSV_PATH, true)) {
                display("A new empty server.database file will be created" + fileWriter.toString());
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void readBuffer(BufferedReader bufferedReader) throws IOException {
        String record;
        while ((record = bufferedReader.readLine()) != null) {
            setRowCount(getRowCount() + 1);
            Employee employee = (Employee) recordHandler.parseRecord(record);
            if (employee != null) {
                Database.getAllEmployees().put(employee.getId(), employee);
                //  setEmployeeLRUCache(employee.getId(), employee);
            }
        }
    }

    public void tryWritingToFile(StringBuilder stringBuilder) {
        try (FileWriter fileWriter = new FileWriter(CSV_PATH, false)) {
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

            display("Data saved to file: " + CSV_PATH);
        } catch (IOException ignored) {
            display("Error: Saving the data is failed!");
        }
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        Database.getAllEmployees().putAll(Database.getEmployeeLRUCache());
        for (Map.Entry<Integer, Employee> entry : Database.getAllEmployees().entrySet()) {
            Employee employee = entry.getValue();
            stringBuilder.append(toRecord(employee));
        }
    }


    public String toRecord(Employee employee) {
        return employee.getId() + ";" +
                employee.getName() + ";" +
                employee.getSalary() + "\n";
    }
}
