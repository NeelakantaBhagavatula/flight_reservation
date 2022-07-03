package sjsu.edu.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sjsu.edu.flightreservation.entities.User;
import sjsu.edu.flightreservation.repos.UserRepository;
import sjsu.edu.flightreservation.services.SecurityService;

@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	SecurityService securityService;

	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		LOGGER.info("Inside showRegistrationPage()");
		return "login/registerUser";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user) {

		LOGGER.info("Executing register() <<, {}", user);
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		LOGGER.info("Exiting register() >>");
		return "login/login";
	}

	@RequestMapping("/showLogin")
	public String showLoginPage() {
		LOGGER.info("Inside showLoginPage()");
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap modelMap) {
		LOGGER.info("Executing login() <<, {}", email);

		boolean isSecuritySuccess = securityService.login(email, password);

		if (isSecuritySuccess) {
			LOGGER.info("Rediecting to find flights page");
			return "findFlights";
		} else {
			modelMap.addAttribute("msg", "Invalid username or password. Please try again.");
		}

		LOGGER.info("Exiting login() >>");
		return "login/login";
	}

}
