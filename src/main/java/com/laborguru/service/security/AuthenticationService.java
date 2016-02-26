package com.laborguru.service.security;

import java.security.Principal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.User;
import com.laborguru.model.UserStatus;
import com.laborguru.service.user.UserService;
import com.mindpool.security.service.UserAuthenticationService;

public class AuthenticationService implements UserAuthenticationService {

	private static final Logger log = Logger.getLogger(AuthenticationService.class);

	private static final int MAX_LOGIN_TRIES = 3;

	private int loginTries = MAX_LOGIN_TRIES;
	
	UserService service;

	private String reason = null;

	public void setService(UserService service) {
		this.service = service;
	}

	public Principal authenticate(String username, String password) {

		UserDetailsImpl userDetails = null;
		User user = new User();
		user.setUserName(username);
		try {
			user = service.getUserByUserName(user);
		} catch (DataAccessException e) {
			log.error("Error trying to get user [" + user.getUserName() + "]", e);
			return null;
		}

		if (user != null) {
			if (!user.isEnabled()) {
				reason = UserAuthenticationService.USER_DISABLED_ERROR;
			} else if (password.equals(user.getPassword())) {
				user.setLastLogon(new Date());
				user.setLoginCount(0);
				userDetails = new UserDetailsImpl(user);
			} else {
				int loginCount = user.getLoginCount();
				user.setLoginCount(++loginCount);
				if (loginCount == loginTries) {
					user.setUserStatus(UserStatus.DISABLED);
					reason = UserAuthenticationService.USER_DISABLED_ERROR;
				} else {
					reason = UserAuthenticationService.BAD_PASSWORD;
				}
			}
			try {
			service.save(user);
			} catch(SpmCheckedException e){
				log.error("Error trying to save user [" + user + "]", e);
			}
		} else {
			reason = UserAuthenticationService.UNKNOWN_USER_ERROR;
		}

		return userDetails;

	}

	/**
	 * 
	 * @return
	 * @see com.mindpool.security.service.UserAuthenticationService#getReason()
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * 
	 * @param loginTries
	 */
	public void setLoginTries(int loginTries){
		this.loginTries = loginTries;
	}

}
