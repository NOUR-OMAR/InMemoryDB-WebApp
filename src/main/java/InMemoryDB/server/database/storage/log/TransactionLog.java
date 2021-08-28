package InMemoryDB.server.database.storage.log;

import InMemoryDB.server.database.storage.FileIOHandler;

import java.io.*;

import static InMemoryDB.utils.Constant.*;
import static InMemoryDB.utils.Constant.Display.display;


public class TransactionLog extends FileIOHandler implements TransactionLogger {


    public TransactionLog() throws IOException {
    }

    /* private static void buildEmployeeRecordString(StringBuilder stringBuilder) {

         for (Integer integer : Database.getTableLRUCache().snapshot().keySet()) {
             if (Database.getTableLRUCache().snapshot() instanceof Employee)

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

    public static String toEmployeeRecord(Employee employee) {
         return employee.getId() + ";" +
                 employee.getName() + ";" +
                 employee.getSalary() + ";" +
                 employee.getDepartment().getId() + "\n";
     }

     public static String toDepartmentRecord(Department department) {
         return department.getId() + ";" +
                 department.getName() + ";" +
                 department.getLocation() + "\n";
     }
 */
    @Override
    public void write(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        if (fileName.equalsIgnoreCase(EMPLOYEES_LOGGER_FILE)) {
            buildEmployeeRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(DEPARTMENTS_LOGGER_FILE)) {
            buildDepartmentRecordString(stringBuilder);
        }
        tryWritingToFile(stringBuilder, fileName);

    }

    @Override
    public void writeToCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(EMPLOYEES_LOGGER_FILE))) {
            readBuffer(bufferedReader, EMPLOYEES_CSV_PATH);

        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided file was not found.A new empty file will be created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DEPARTMENTS_LOGGER_FILE))) {
            readBuffer(bufferedReader, DEPARTMENTS_CSV_PATH);

        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided file was not found.A new empty file will be created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    /*private void tryWritingToFile(StringBuilder stringBuilder, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(stringBuilder.toString());
            myWriter.close();
            display("Successfully wrote to the file.");
        } catch (IOException e) {
            display("An error occurred.");
            e.printStackTrace();
        }
    }*/

    private void readBuffer(BufferedReader bufferedReader, String filePath) throws IOException {
        String row;
        try (FileWriter myWriter = new FileWriter(filePath, false)) {
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


