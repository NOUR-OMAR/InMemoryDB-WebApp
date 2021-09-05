package InMemoryDB.security;

import InMemoryDB.database.users_table.UserTableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserTableDAO userTableDAO;
    @Autowired
    UserRoles userRoles;

   @PostConstruct
    public void init() throws IOException {
       userRoles.roles.put("ADMIN", new User(userTableDAO.readUser("admin123").getUsername(), userTableDAO.readUser("admin123").getPassword(), userRoles.getAuthority("ROLE_ADMIN")));
        for (InMemoryDB.model.User user : userTableDAO.selectAll()) {
            if (user.getRole().equals("EMPLOYEE"))
                userRoles.roles.put("EMPLOYEE", new User(user.getUsername(), user.getPassword(), userRoles.getAuthority("ROLE_USER")));
        }

    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        user = userRoles.roles.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User");
        }

        return user;
    }

}
