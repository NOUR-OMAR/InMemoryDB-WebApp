package InMemoryDB.security;

import InMemoryDB.database.users_table.UserTableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final Map<String, User> roles = new HashMap<>();
    @Autowired
    UserTableDAO userTableDAO;

    @PostConstruct
    public void init() throws IOException {
        roles.put("ADMIN", new User(userTableDAO.readUser("admin123").getUsername(), userTableDAO.readUser("admin123").getPassword(), getAuthority("ROLE_ADMIN")));
        for (InMemoryDB.model.User user : userTableDAO.selectAll()) {
            if (user.getRole().equals("EMPLOYEE"))
                roles.put("EMPLOYEE", new User(user.getUsername(), user.getPassword(), getAuthority("ROLE_USER")));
        }

    }


    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        user = roles.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find User");
        }

        return user;
    }

}
