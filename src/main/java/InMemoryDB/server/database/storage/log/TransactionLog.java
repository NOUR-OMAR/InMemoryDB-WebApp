package InMemoryDB.server.database.storage.log;

import InMemoryDB.client.model.Employee;
import InMemoryDB.server.database.Database;

import java.io.*;
import java.util.Map;

import static InMemoryDB.utils.Constant.CSV_PATH;
import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.LOGGER_FILE;


public class TransactionLog implements TransactionLogger {


    public TransactionLog() throws IOException {
    }

    private static void stringBuilder(StringBuilder stringBuilder) {
        Database.getAllEmployees().putAll(Database.getEmployeeLRUCache());
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
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LOGGER_FILE))) {
            readBuffer(bufferedReader);

        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided file was not found.A new empty file will be created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void tryWritingToFile(StringBuilder stringBuilder) {
        try {
            FileWriter myWriter = new FileWriter(LOGGER_FILE);
            myWriter.write(stringBuilder.toString());
            myWriter.close();
            display("Successfully wrote to the file.");
        } catch (IOException e) {
            display("An error occurred.");
            e.printStackTrace();
        }
    }

    private void readBuffer(BufferedReader bufferedReader) throws IOException {
        String row;
        try (FileWriter myWriter = new FileWriter(CSV_PATH, false)) {
            while ((row = bufferedReader.readLine()) != null) {

                myWriter.write(row + "\n");
            }
            display("Successfully wrote to the file.");
        } catch (IOException e) {
            display("An error occurred.");
            e.printStackTrace();
        }
    }
}


