package com.laborguru.service.user.dao;

import java.util.List;

import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.model.filter.SearchUserFilter;


/**
 * UserDao interface
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface UserDao {

	/**
	 * Save or Update a user
	 * @param user object that will be save.
	 * @return user Saved.
	 */
	User save(User user);
	/**
	 * Retrieves  a user by userName
	 * @param user with username populated
	 * @return a full populated user
	 */
	User getUserByUsername(User user);
	
	
	/**
	 * True when the user with userName passed as parameter already exists in the Database 
	 * @param userName The userName 
	 * @return true if the user exist
	 */
	Boolean existUser(String userName);
	
	
	/**
	 * Deattaches a persistence instance of User from the hibernate session  
	 * @param auxUser
	 */
	void evict(User auxUser);
	
	/**
	 * Retrieves a user by id.
	 * @param user
	 * @return
	 */
	User getUserById(User user);
	
	/**
	 * Finds all users that are not employee.
	 * @return
	 */
	List<User> findUsersByProfile(Profile profile);

	/**
	 * finds a user by name and surname
	 * @param searchUserFilter
	 * @return
	 */
	List<User>applyFilters(SearchUserFilter searchUserFilter);
	/**
	 * Deletes a user.
	 * @param user
	 */
	void delete(User user);
	
	
	/**
	 * Retrieve the number of users that are available in the system
	 * 
	 * @return the number of users
	 */
	Integer getNumberOfUsers();
	
	
	/**
	 * Retrieve the number of enabled users that are available in the system
	 * 
	 * @return the number of enabled users
	 * @see com.laborguru.service.user.dao.UserDao#getNumberOfUsers()
	 */
	Integer getNumberOfEnabledUsers();
}
