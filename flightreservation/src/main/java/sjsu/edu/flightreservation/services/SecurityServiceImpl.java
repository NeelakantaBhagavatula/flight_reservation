package sjsu.edu.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public boolean login(String username, String password) {
		LOGGER.info("Executing SecurityService login() << {}", username);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password,
				userDetails.getAuthorities());
		
		authenticationManager.authenticate(token);
		
		boolean isAuthenticated = token.isAuthenticated();
		
		if (isAuthenticated) {
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		
		LOGGER.info("Exiting SecurityService login() >> {}", username);
		
		return isAuthenticated;
	}

}
