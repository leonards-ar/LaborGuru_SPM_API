package com.laborguru.service.security;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;


import com.laborguru.model.User;
import com.laborguru.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AuthenticationService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	private UserService service;

	public void setService(UserService service) {
		this.service = service;
	}

	public UserDetails loadUserByUsername(String username) {

		UserDetailsImpl userDetails = null;
		User user = new User();
		user.setUserName(username);
		try {
			user = service.getUserByUserName(user);
		} catch (DataAccessException e) {
			log.error("Error trying to get user [" + user.getUserName() + "]", e);
			return null;
		}

		return new UserDetailsImpl(user);


	}

}
