package InMemoryDB.database.storage.log;

import InMemoryDB.database.storage.FileIOHandler;

import java.io.*;

import static InMemoryDB.utils.Constant.*;
import static InMemoryDB.utils.Constant.Display.display;


public class TransactionLog extends FileIOHandler implements TransactionLogger {


    public TransactionLog() throws IOException {
    }

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


