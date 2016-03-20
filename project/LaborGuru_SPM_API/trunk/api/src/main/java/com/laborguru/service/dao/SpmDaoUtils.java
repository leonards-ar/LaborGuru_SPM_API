package com.laborguru.service.dao;

/**
 * Provides with persitance layer utility methods thought to be used from the services layer.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface SpmDaoUtils {

	/**
	 *  This methods clear the session. Empty the complete first level of hibernate cache.
	 */
	void clearSession();

	/**
	 *  This methods flush the session to the database.
	 */
	void flushSession();
}
