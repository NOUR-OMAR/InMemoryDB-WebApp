package InMemoryDB.security;

import InMemoryDB.database.users_table.UserTableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserTableDAO userTableDAO;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryDB.model.User user = null;
        try {
            user = userTableDAO.readUser(username);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User");
        }
        return new NewUserDetails(user);
    }

}
