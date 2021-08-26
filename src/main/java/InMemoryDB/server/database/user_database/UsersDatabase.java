package InMemoryDB.server.database.user_database;

import InMemoryDB.client.model.User;
import InMemoryDB.server.database.storage.UsersFile;

import java.util.HashMap;

public class UsersDatabase {

    private static final HashMap<String, User> allUsers = new HashMap<>();
    public static UsersDatabase usersDatabase;

    UsersFile usersFile = new UsersFile();

    public UsersDatabase() {
        usersFile.initialize();

    }

    public static synchronized UsersDatabase getUsersDatabase() {
        if (UsersDatabase.usersDatabase == null) {
            UsersDatabase.usersDatabase = new UsersDatabaseBuilder().createUsersTable();
        }
        return UsersDatabase.usersDatabase;
    }

    public static HashMap<String, User> getAllUsers() {
        return UsersDatabase.allUsers;

    }


    public void close() {
        StringBuilder stringBuilder = new StringBuilder();
        usersFile.setStringBuilder(stringBuilder);
        usersFile.tryWritingToFile(stringBuilder);

    }

    public User get(String username) {

        return getAllUsers().get(username);

    }

    public void put(User user) {
        synchronized (getAllUsers()) {
            getAllUsers().put(user.getUsername(), user);

        }
    }
}




