package InMemoryDB.database.storage.log;

import InMemoryDB.database.storage.FilesPaths;
import InMemoryDB.database.storage.FileIOHandler;

import java.io.*;

import static InMemoryDB.utils.Display.display;


public class TransactionLog extends FileIOHandler implements TransactionLogger {


    public TransactionLog() throws IOException {
    }

    @Override
    public void write(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (fileName.equalsIgnoreCase(FilesPaths.EMPLOYEES_LOGGER_FILE.getPath())) {
            buildEmployeeRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(FilesPaths.DEPARTMENTS_LOGGER_FILE.getPath())) {
            buildDepartmentRecordString(stringBuilder);
        } else if (fileName.equalsIgnoreCase(FilesPaths.USERS_LOGGER_FILE.getPath())) {
            buildUsersRecordString(stringBuilder);
        }
        tryWritingToFile(stringBuilder, fileName);

    }

    @Override
    public void writeToCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FilesPaths.EMPLOYEES_LOGGER_FILE.getPath()))) {
            readBuffer(bufferedReader, FilesPaths.EMPLOYEES_CSV_PATH.getPath());

        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided file was not found.A new empty file will be created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FilesPaths.DEPARTMENTS_LOGGER_FILE.getPath()))) {
            readBuffer(bufferedReader, FilesPaths.DEPARTMENTS_CSV_PATH.getPath());

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


