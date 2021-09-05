package InMemoryDB.database.users_table;

import InMemoryDB.database.Database;
import InMemoryDB.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static InMemoryDB.utils.Constant.Display.display;

@Data
@Component
public class UserTableDAOImpl implements UserTableDAO {
    @Autowired
    private final Database database;



    @Override
    public User createUser(User user) throws IOException {

        if (database.getUser(user.getUsername()) == null) {

            database.putInUsersTable(user);
           return user;
        } else {
            display("Can't create, user with username " + user.getUsername()+ " already existed.");
          return null;
        }

    }

    @Override
    public User readUser(String username) throws IOException {
         return database.getUser(username);
    }

    @Override
    public void close() throws IOException {
        database.closeUsersTB();

    }

    @Override
    public List<User> selectAll() throws IOException {
        return new ArrayList<>(database.getAllUsers().values());

    }
}
