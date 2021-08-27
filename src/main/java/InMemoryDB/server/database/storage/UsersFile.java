package InMemoryDB.server.database.storage;

import InMemoryDB.client.model.User;
import InMemoryDB.server.database.record.RecordHandler;
import InMemoryDB.server.database.record.UsersRecord;
import InMemoryDB.server.database.user_database.UsersDatabase;

import java.io.*;
import java.util.Map;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.USERS_FILE_PATH;

public class UsersFile implements FileHandler {
    RecordHandler recordHandler = new UsersRecord();


    public void tryWritingToFile(StringBuilder stringBuilder) {
        try (FileWriter fileWriter = new FileWriter(USERS_FILE_PATH, false)) {
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

            display("User saved to file: " + USERS_FILE_PATH);
        } catch (IOException ignored) {
            display("Error: Saving the data is failed!");
        }
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        for (Map.Entry<String, User> entry : UsersDatabase.getAllUsers().entrySet()) {
            User user = entry.getValue();
            stringBuilder.append(user.getUsername()).append(";").append(user.getPassword()).append("\n");
        }
    }

    @Override
    public void initialize() {
        display("Loading data from file " + USERS_FILE_PATH);
        loadData();

    }

    private void loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            readBuffer(bufferedReader);

        } catch (FileNotFoundException fileNotFoundException) {
            display("The provided csv file was not found.A new empty file will be created");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void readBuffer(BufferedReader bufferedReader) throws IOException {
        String row;
        int iteration = 0;
        while ((row = bufferedReader.readLine()) != null) {
            if (iteration == 0) {
                iteration++;
                continue;
            }
            User user = (User) recordHandler.parseRecord(row);
            if (user != null) {

                UsersDatabase.getAllUsers().put(user.getUsername(), user);
            }
        }
        display("Users are loaded");
    }


}
