package com.laborguru.service.user.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.model.filter.SearchUserFilter;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;



 
/**
 * Implementation for UserDao
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UserDaoHibernate extends SpmHibernateDao implements UserDao {
	
	private static Logger log = Logger.getLogger(UserDaoHibernate.class);
	
	
	/* (non-Javadoc)
	 * @see com.laborguru.service.user.dao.UserDao#save(com.laborguru.model.User)
	 */
	public User save(User user) {
		if(log.isDebugEnabled()) {
			log.debug("Updating user: " + user);
		}
		getHibernateTemplate().saveOrUpdate(user);
		return user;
	}

	/* (non-Javadoc)
	 * @see com.laborguru.service.user.dao.UserDao#getUserByUsername(com.laborguru.model.User)
	 */
	public User getUserByUsername(User user) {

		List<User> result = (List<User>)getHibernateTemplate().findByNamedParam(
				"from User user where user.userName = :searchString and user.status != 2", "searchString",user.getUserName());

		User retUser = null;
		
		if ( result.size() > 0)
			retUser = result.get(0);
		
		return retUser;
	}

	/**
	 * True when the user with userName passed as parameter already exists in the Database 
	 * @param userName The userName 
	 * @return true if the user exist
	 * @see com.laborguru.service.user.dao.UserDao#existUser(java.lang.String)
	 */
	public Boolean existUser(String username){
		List<String> result = (List<String>)getHibernateTemplate().findByNamedParam(
				"select user.userName from User user where user.userName = :searchString and user.status != 2", "searchString",username);
				
		return result.size() != 0;
	}

	/**
	 * Evicts the persistence instance passed in as parameter.
	 * @param auxUser
	 * @see com.laborguru.service.user.dao.UserDao#evict(com.laborguru.model.User)
	 */
	public void evict(User auxUser) {
		getHibernateTemplate().evict(auxUser);
	}
	
	/**
	 * Retrieves a User by id.
	 * @param user
	 * @return
	 * @see com.laborguru.service.user.dao.UserDao#getUserById(com.laborguru.model.User)
	 */
	public User getUserById(User user) {
		return (User)getHibernateTemplate().get(User.class, user.getId());
	}
	
	/**
	 * Finds all users that are not employee.
	 * @return
	 * @see com.laborguru.service.user.dao.UserDao#findAdminUsers()
	 */
	public List<User> findUsersByProfile(Profile profile){
		return (List<User>)getHibernateTemplate().findByNamedParam("select u from User as u join u.profiles as profile where profile.id = :searchString  and u.status != 2", "searchString", profile.getId());
	}

	/**
	 * 
	 * @param searchUserFilter
	 * @return
	 * @see com.laborguru.service.user.dao.UserDao#applyFilters(com.laborguru.model.filter.SearchUserFilter)
	 */
	public List<User>applyFilters(SearchUserFilter searchUserFilter) {
		
		StringBuilder sb = new StringBuilder();
		
		
		sb.append("select user from User as user ");
		
		if(includeInFilter(searchUserFilter.getProfile())) {
			sb.append(" join user.profiles as profile where ");
			sb.append(" profile.id=" + searchUserFilter.getProfile().getId());
		}
		
		if(includeInFilter(searchUserFilter.getFullName())) {
			if(sb.indexOf("where") >= 0) {
				sb.append(" and (user.name like '%" + searchUserFilter.getFullName() + "%'");
			} else {
				sb.append(" where (user.name like '%" + searchUserFilter.getFullName() + "%'");
			}
			sb.append(" or user.surname like '%" + searchUserFilter.getFullName() + "%')");
		}

		if(sb.indexOf("where") >= 0) {
			sb.append(" and");
		}
		sb.append(" user.status != 2");
		

		if(log.isDebugEnabled()) {
			log.debug(sb.toString());
		}
		
		return (List<User>)getHibernateTemplate().find(sb.toString());
	}
	
	/**
	 * 
	 * @param user
	 * @see com.laborguru.service.user.dao.UserDao#delete(com.laborguru.model.User)
	 */
	public void delete(User user){
		getHibernateTemplate().delete(user);
	}

	/**
	 * Retrieve the number of users that are available in the system
	 * 
	 * @return the number of users
	 * @see com.laborguru.service.user.dao.UserDao#getNumberOfUsers()
	 */
	public Integer getNumberOfUsers() {
		List<Long> results = (List<Long>)getHibernateTemplate().find("select count(*) from User where status != 2");
		Long retVal = results.get(0);
		
		return Integer.valueOf(retVal.intValue());
	}
	
	/**
	 * Retrieve the number of enabled users that are available in the system
	 * 
	 * @return the number of enabled users
	 * @see com.laborguru.service.user.dao.UserDao#getNumberOfUsers()
	 */
	public Integer getNumberOfEnabledUsers() {
		List<Long> results = (List<Long>)getHibernateTemplate().find("select count(*) from User where status = 0");
		Long retVal = results.get(0);
		
		return Integer.valueOf(retVal.intValue());
	}
}
