package InMemoryDB.server.database.storage.log;

import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.Database;

import java.io.*;
import java.util.Map;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.EMPLOYEES_CSV_PATH;
import static InMemoryDB.utils.Constant.EMPLOYEES_LOGGER_FILE;


public class TransactionLog implements TransactionLogger {


    public TransactionLog() throws IOException {
    }

    private static void stringBuilder(StringBuilder stringBuilder) {
        for (Integer integer : Database.getTableLRUCache().keySet()) {
            Database.getAllEmployees().put(integer, (Employee) Database.getTableLRUCache().get(integer));
        }
        //  Database.getAllEmployees().putAll((Map<? extends Integer, ? extends Employee>) Database.getTableLRUCache().values());
        for (Map.Entry<Integer, Employee> entry : Database.getAllEmployees().entrySet()) {
            Employee employee = entry.getValue();
            stringBuilder.append(toRecord(employee));
        }
    }

    public static String toRecord(Employee employee) {
        return employee.getId() + ";" + employee.getName() + ";" + employee.getSalary() + "\n";
    }

    @Override
    public void write() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder(stringBuilder);
        tryWritingToFile(stringBuilder);

    }

    @Override
    public void writeToCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(EMPLOYEES_LOGGER_FILE))) {
            readEmployeeBuffer(bufferedReader);

        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided file was not found.A new empty file will be created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void tryWritingToFile(StringBuilder stringBuilder) {
        try {
            FileWriter myWriter = new FileWriter(EMPLOYEES_LOGGER_FILE);
            myWriter.write(stringBuilder.toString());
            myWriter.close();
            display("Successfully wrote to the file.");
        } catch (IOException e) {
            display("An error occurred.");
            e.printStackTrace();
        }
    }

    private void readEmployeeBuffer(BufferedReader bufferedReader) throws IOException {
        String row;
        try (FileWriter myWriter = new FileWriter(EMPLOYEES_CSV_PATH, false)) {
            while ((row = bufferedReader.readLine()) != null) {

                myWriter.write(row + "\n");
            }
            display("Successfully wrote to the file.");
        } catch (IOException e) {
            display("An error occurred.");
            e.printStackTrace();
        }
    }


    public void loadEmployeeData() throws IOException {
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
}


