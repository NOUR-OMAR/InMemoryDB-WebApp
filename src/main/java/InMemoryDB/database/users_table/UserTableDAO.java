package InMemoryDB.database.users_table;

import InMemoryDB.model.User;

import java.io.IOException;
import java.util.List;

public interface UserTableDAO {

    User createUser(User user) throws IOException;

    User readUser(String username) throws IOException;

    void close() throws IOException;

    List<User> selectAll() throws IOException;


}
