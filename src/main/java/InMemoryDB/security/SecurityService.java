package InMemoryDB.security;


import InMemoryDB.database.users_table.UserTableDAO;
import InMemoryDB.model.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log
@Service
public class SecurityService {
    @Autowired
    UserTableDAO userTableDAO;
    boolean authenticated = false;

    public boolean authenticate(String username, String password) throws IOException {
        for (User user : userTableDAO.selectAll()) {

            authenticated = (userTableDAO.readUser(username).getUsername().equals(user.getUsername()) && userTableDAO.readUser(username).getPassword().equals(password));

        }


        log.info(String.format("SecurityService: %s",
                authenticated ? "User has been authenticated" : "User failed to authenticate"));

        return authenticated;

    }

}
