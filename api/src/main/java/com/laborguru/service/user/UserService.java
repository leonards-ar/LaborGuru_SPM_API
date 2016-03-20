package com.laborguru.service.user;

import java.util.List;

import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.model.filter.SearchUserFilter;
import com.laborguru.service.Service;
import com.laborguru.service.user.dao.UserDao;

/**
 * User Service Interface. Handles services for SPM Users.  
 *
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface UserService extends Service {

	/**
	 * Save or Update a user
	 * @param user object that will be save.
	 * @return user Saved.
	 */
	User save(User user) throws SpmCheckedException;

	/**
	 * Update a user password
	 * @param user object that will contain the new password to save
	 * @return user Saved.
	 */
	User resetPassword(User user) throws SpmCheckedException;
	
	/**
	 * Retrieves  a user by userName
	 * @param A User object containing a username.
	 * @return A full populated User object.
	 */
	User getUserByUserName(User user);

	/**
	 * Finds all the users that are not employee.
	 * @return
	 */
	List<User> findUsersByProfile(Profile profile);
	
	/**
	 * Deletes a user.
	 * @param user
	 */
	void delete(User user);
	
	/**
	 * 
	 * @param user
	 * @throws SpmCheckedException
	 */
	void logicalDelete(User user) throws SpmCheckedException;
	
	/**
	 * Retrieves a user by id
	 * @param user
	 * @return
	 */
	User getUserById(User user);

	/**
	 * Searches a user by name and surname
	 * @param searchUserFilter
	 * @return
	 */
	List<User> filterUser(SearchUserFilter searchUserFilter);
	/**
	 * Setter for User Dao
	 * @param userDao UserDao
	 */
	void setUserDao(UserDao userDao);

	/**
	 * Get number of users
	 * @return
	 */
	Integer getNumberOfUsers();

	/**
	 * Retrieve the number of enabled users that are available in the system
	 * 
	 * @return the number of enabled users
	 */
	Integer getNumberOfEnabledUsers();
}
