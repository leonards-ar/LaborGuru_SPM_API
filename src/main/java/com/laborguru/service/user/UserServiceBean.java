package com.laborguru.service.user;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.model.UserStatus;
import com.laborguru.model.filter.SearchUserFilter;
import com.laborguru.service.user.dao.UserDao;

/**
 * Spring Implementation for UserService
 * @author cnunez
 *
 */
public class UserServiceBean implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceBean.class);
	
	public static final String USER_NULL = "The user passed as parameter cannot be can not be null";
	public static final String USER_NAME_NULL = "The user passed as parameter cannot have null username";
	
	private static final int PASSWORD_LENGTH = 8;
	
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see com.laborguru.service.user.UserService#save(com.laborguru.model.User)
	 */
	public User save(User user) throws SpmCheckedException{
		
		User retUser = null;
		
		if(user == null) 
		{
			log.error("Employee passed in as parameter is null");
			throw new IllegalArgumentException("Employee passed in as parameter is null");				
		}
		
		if(log.isDebugEnabled()){
			log.debug("before save employee:"+ user);
		}

		retUser = user.getId()!= null? update(user):create(user);
		
		if(log.isDebugEnabled()){
			log.debug("after save employee:"+ user);
		}
		
		return retUser;
	}
	

	/**
	 * @param userLoggingOn
	 * @return
	 */
	public User getUserByUserName(User userLoggingOn) {
		if (userLoggingOn == null)
			throw new IllegalArgumentException(USER_NULL);
		
		if (userLoggingOn.getUserName() == null)
			throw new IllegalArgumentException(USER_NAME_NULL);
		
				
		return userDao.getUserByUsername(userLoggingOn);
	}
	
	public List<User> findUsersByProfile(Profile profile){
		return userDao.findUsersByProfile(profile);
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @see com.laborguru.service.user.UserService#getUserById(com.laborguru.model.User)
	 */
	public User getUserById(User user) {
		return this.userDao.getUserById(user);
	}
	
	
	/**
	 * Creates employee
	 */
	private User create(User user) throws SpmCheckedException {		
							
		//Checking if user name already exist
		if (userDao.existUser(user.getUserName()))
		{
			String exMgs = "username: "+ user.getUserName()+" already exist in the database";
			log.error(exMgs);
			throw new SpmCheckedException(exMgs, ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[]{user.getUserName()});
		}
		user.setCreationDate(new Date());
		return  userDao.save(user);
	}

	/**
	 * Updates User
	 */
	private User update(User user) throws SpmCheckedException {
							
		//Checking if user name already exist
		User auxUser = userDao.getUserByUsername(user);
		
		if ((auxUser != null) && !auxUser.getId().equals(user.getId()))
		{
			String exMgs = "username: "+ user.getUserName()+" already exists in the database";
			log.error(exMgs);
			throw new SpmCheckedException(exMgs, ErrorEnum.USERNAME_ALREADY_EXIST_ERROR, new String[]{user.getUserName()});
			
		}
		
		//Evicting the user
		userDao.evict(auxUser);
		
		user.setLastUpdateDate(new Date());
		return userDao.save(user);
	}
	
	/**
	 * Deletes a user.
	 * @param user
	 * @see com.laborguru.service.user.UserService#delete(com.laborguru.model.User)
	 */
	public void delete(User user) {
		userDao.delete(user);
	}
	
	/**
	 * Searches a user by name and surname
	 * @param searchUserFilter
	 * @return
	 */
	public List<User> filterUser(SearchUserFilter searchUserFilter) {
		
		if(searchUserFilter == null) {
			log.error("The filter passed as parameter is null");
			throw new IllegalArgumentException("The filter passed as parameter is null");
		}
		
		return userDao.applyFilters(searchUserFilter);
	}

	/**
	 * 
	 * @param userDao
	 * @see com.laborguru.service.user.UserService#setUserDao(com.laborguru.service.user.dao.UserDao)
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 
	 * @return
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.user.UserService#resetPassword(com.laborguru.model.User)
	 */
	public User resetPassword(User user) throws SpmCheckedException {
		user.setPassword(generatePassword(user));
		update(user);
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private String generatePassword(User user) {
		final String characters = "qw37er#!tyu2ioplk84jhgfds$%azxcv+bnm01956*_";
		StringBuffer newPassword = new StringBuffer();
		
		Random rand = new Random();
		
		for(int i = 0; i < PASSWORD_LENGTH; i++) {
			newPassword.append(characters.charAt(Math.abs(rand.nextInt() % characters.length())));
		}
		
		return newPassword.toString();
	}


	/**
	 * Retrieve the number of users that are available in the system
	 * @return
	 * @see com.laborguru.service.user.UserService#getNumberOfUsers()
	 */
	public Integer getNumberOfUsers() {
		return userDao.getNumberOfUsers();
	}


	/**
	 * Retrieve the number of enabled users that are available in the system
	 * 
	 * @return the number of enabled users
	 * @see com.laborguru.service.user.UserService#getNumberOfEnabledUsers()
	 */
	public Integer getNumberOfEnabledUsers() {
		return userDao.getNumberOfEnabledUsers();
	}

	/**
	 * 
	 * @param user
	 * @throws SpmCheckedException
	 * @see com.laborguru.service.user.UserService#logicalDelete(com.laborguru.model.User)
	 */
	public void logicalDelete(User user) throws SpmCheckedException {
		if(user == null) {
			log.error("User passed as parameter is null");
			throw new IllegalArgumentException("param is null");
		}
		if(log.isDebugEnabled()) {
			log.debug("About to logically delete user " + user);
		}
		user.setUserStatus(UserStatus.DELETED);
		user.setUserName(user.getUserName() + "_" + user.getId());
		save(user);
	}
}
