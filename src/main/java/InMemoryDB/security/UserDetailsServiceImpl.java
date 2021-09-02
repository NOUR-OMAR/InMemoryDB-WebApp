package InMemoryDB.security;

import InMemoryDB.database.Database;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailsServiceImpl implements UserDetailsService {

	private Map<String, User> roles = new HashMap<>();

	@PostConstruct
	public void init() {
		roles.put("ADMIN", new User(Database.getAllUsers().get("admin123").getUsername(), Database.getAllUsers().get("admin123").getPassword(), getAuthority("ROLE_ADMIN")));
		for (InMemoryDB.model.User user : Database.getAllUsers().values()) {
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
		if(user==null) {
			throw new UsernameNotFoundException("Could not find User");
		}

		return user;
	}

}
