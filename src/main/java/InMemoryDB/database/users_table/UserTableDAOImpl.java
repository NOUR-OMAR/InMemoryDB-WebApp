package InMemoryDB.database.users_table;

import InMemoryDB.database.Database;
import InMemoryDB.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static InMemoryDB.database.Database.getDatabase;
import static InMemoryDB.utils.Constant.Display.display;
@Service

public class UserTableDAOImpl implements UserTableDAO {
    private final Database database;

    public UserTableDAOImpl() throws IOException {
        this.database = Database.getInitialisedDatabase();
    }

    @Override
    public User createUser(User user) throws IOException {

        if (getDatabase().getUser(user.getUsername()) == null) {

    // Employee newEmployee = new Employee(user.getId(), user.getUsername(), user.getPassword(),user.getRole());

            getDatabase().putInUsersTable(user);
           return user;
        } else {
            display("Can't create, user with username " + user.getUsername()+ " already existed.");
          return null;
        }

    }

    @Override
    public User readUser(String username) throws IOException {
         return getDatabase().getUser(username);
    }

    @Override
    public void close() throws IOException {
        Database.getInitialisedDatabase().closeUsersTB();

    }

    @Override
    public List<User> selectAll() throws IOException {
        List<User> users = new ArrayList<>();
        users.addAll(Database.getAllUsers().values());
        return users;

    }
}
