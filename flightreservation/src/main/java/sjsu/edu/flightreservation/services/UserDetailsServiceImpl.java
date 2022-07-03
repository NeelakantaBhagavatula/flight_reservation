package sjsu.edu.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sjsu.edu.flightreservation.entities.User;
import sjsu.edu.flightreservation.repos.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("Executing loadUserByUsername() << {}", username);
		
		User user = userRepository.findByEmail(username);
		
		if (user == null) {
			LOGGER.error("User not found {}", username);
			throw new UsernameNotFoundException("User not found : " +username);
		}
		LOGGER.info("Exiting loadUserByUsername() >> {}", username);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());
	}

}
