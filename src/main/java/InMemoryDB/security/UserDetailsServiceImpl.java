package InMemoryDB.security;

import InMemoryDB.model.User;
import InMemoryDB.database.Database;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

public class UserDetailsServiceImpl implements UserDetailsService {
	

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = Database.getDatabase().getUser(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(user==null) {
			throw new UsernameNotFoundException("Could not find User");
		}
		return new UserDetailsReader(user);
	}

}
