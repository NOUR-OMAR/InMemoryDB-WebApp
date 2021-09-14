package InMemoryDB.database.users_table;

import InMemoryDB.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static InMemoryDB.database.Database.getDatabase;
import static InMemoryDB.utils.Display.display;

@Data
@Component
public class UserTableDAOImpl implements UserTableDAO {
    @Override
    public User createUser(User user) throws IOException {

        if (getDatabase().getUser(user.getUsername()) == null) {

            getDatabase().putInUsersTable(user);
            return user;
        } else {
            display("Can't create, user with username " + user.getUsername() + " already existed.");
            return null;
        }

    }

    @Override
    public User readUser(String username) throws IOException {
        return getDatabase().getUser(username);
    }

    @Override
    public void close() throws IOException {
        getDatabase().closeUsersTB();

    }

    @Override
    public List<User> selectAll() throws IOException {
        return new ArrayList<>(getDatabase().getAllUsers().values());

    }
}
