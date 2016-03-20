package com.laborguru.service.dao.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laborguru.service.dao.SpmDaoUtils;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * Hibernate DAO Supperclass
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class SpmHibernateDao  implements SpmDaoUtils {

	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;

	private final Logger log = LoggerFactory.getLogger((SpmHibernateDao.class));

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
		getSessionFactory().getCurrentSession().clear();
	}

	/**
	 *  This methods flush the session to the database.
	 */
	public void flushSession(){
		getSessionFactory().getCurrentSession().flush();
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

	/**
	 * This is a wrapper to adapt hibernate4 with the old configuration.
	 * @return
	 */

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
}
