package com.laborguru.service.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.service.dao.SpmDaoUtils;

/**
 * Hibernate DAO Supperclass
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SpmHibernateDao extends HibernateDaoSupport implements SpmDaoUtils {

	/**
	 * 
	 */
	public SpmHibernateDao() {
	}
	
	/**
	 * This method checks if an Object value must be included
	 * in a filter.
	 * @param value The value to check.
	 * @return If the value must be taken into account in the filter
	 */
	protected boolean includeInFilter(Object value) {
		return value != null;
	}
	
	/**
	 * This method checks if a String value must be included
	 * in a filter.
	 * @param value The value to check.
	 * @return If the value must be taken into account in the filter
	 */
	protected boolean includeInFilter(String value) {
		return includeInFilter((Object)value) && value.length() > 0;
	}

	/**
	 * This method checks if a String value must be included
	 * in a filter.
	 * @param value The value to check.
	 * @return If the value must be taken into account in the filter
	 */
	protected boolean includeInFilter(Integer value) {
		return includeInFilter((Object)value) && value.intValue() >= 0;
	}	
	
	/**
	 *  This methods clear the session. Empty the complete first level of hibernate cache.
	 */
	public void clearSession(){
		getHibernateTemplate().clear();
	}

	/**
	 *  This methods flush the session to the database.
	 */
	public void flushSession(){
		getHibernateTemplate().flush();
	}
	
	/**
	 * Validate wheter ther argument passed as parameter is null and throws an IllegalArgumentException exception
	 * 
	 * @param the argument
	 * @param The name of the argumente
	 * @param The logger
	 */
	protected void checkNullArgumentAndThrowException(Object arg, String nameArg, Logger log){
		if (arg == null){
			String msg = "The"+ nameArg + " passed in as parameter is null";
			log.error(msg);
			throw new IllegalArgumentException(msg);			
		}
	}
	
}
