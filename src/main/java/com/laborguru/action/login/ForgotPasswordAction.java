/*
 * File name: ForgotPasswordAction.java
 * Creation date: 15/11/2008 11:27:23
 * Copyright Mindpool
 */
package com.laborguru.action.login;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.User;
import com.laborguru.service.email.EmailService;
import com.laborguru.service.user.UserService;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ForgotPasswordAction extends SpmAction {
	private static final Logger log = Logger.getLogger(ForgotPasswordAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3508141526333375102L;
	private String username;
	private UserService userService;
	private EmailService emailService;
	
	/**
	 * 
	 */
	public ForgotPasswordAction() {
	}

	/**
	 * 
	 * @return
	 */
	public String execute() {
		try {
			if(getUsername() != null && getUsername().trim().length() > 0) {
				User user = new User();
				user.setUserName(getUsername());
				user = getUserService().getUserByUserName(user);
				
				if(user != null) {
					if(!StringUtils.isEmpty(user.getEmail())) {
						user = getUserService().resetPassword(user);
						
						notifyNewPassword(user);
						
						addActionMessage(getText("login.forgot_password.message_sent", new String[] {user.getEmail()}));
					} else {
						addActionError(getText("login.forgot_password.email.empty", new String[] {getUsername()}));
					}
				} else {
					addActionError(getText("login.forgot_password.username.invalid", new String[] {getUsername()}));
				}
			} else {
				addActionError(getText("login.forgot_password.username.empty"));
			}
		} catch(SpmCheckedException ex) {
			log.error(ex.getMessage(), ex);
			addActionError(ex.getErrorMessage());
		}
		
		return SpmActionResult.LOGIN.getResult();
	}
	

	/**
	 * 
	 * @param user
	 * @throws SpmCheckedException
	 */
	private void notifyNewPassword(User user) throws SpmCheckedException {
		try {
			String to = user.getEmail();
			String subject = getText("login.forgot_password.email.subject", new String[] {user.getFullName(), user.getUserName()});
			String body = getText("login.forgot_password.email.body", new String[] {user.getName(), user.getSurname(), user.getFullName(), user.getUserName(), user.getPassword(), getRemoteAddress()});
			getEmailService().sendEmail(new String[]{to}, null, subject, body);
		} catch(Throwable ex) {
			log.error("Cannot send new password to user [" + user.getUserName() + "]", ex);
		}
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the emailService
	 */
	public EmailService getEmailService() {
		return emailService;
	}

	/**
	 * @param emailService the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
