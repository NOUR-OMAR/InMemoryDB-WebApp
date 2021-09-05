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
    @Autowired
    UserRoles userRoles;

 /*@PostConstruct
    public void init() throws IOException {
       userRoles.roles.put("ADMIN", new User(userTableDAO.readUser("admin123").getUsername(), userTableDAO.readUser("admin123").getPassword(), userRoles.getAuthority("ADMIN")));
        for (InMemoryDB.model.User user : userTableDAO.selectAll()) {
            if (user.getRole().equals("EMPLOYEE"))
                userRoles.roles.put("EMPLOYEE", new User(user.getUsername(), user.getPassword(), userRoles.getAuthority("EMPLOYEE")));
        }

    }

*/



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryDB.model.User user = null;
        System.out.println(username);
        try {
            user = userTableDAO.readUser(username);
            System.out.println(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User");
        }
      //  System.out.println(new NewUserDetails(user).getAuthorities());
        return new NewUserDetails(user);
    }

}
