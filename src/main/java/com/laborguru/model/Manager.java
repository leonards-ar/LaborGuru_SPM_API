package com.laborguru.model;

import java.util.Date;


/**
 * Interface implemented for 
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface Manager {

	Integer getId();

	void setId(Integer id);

	String getUserName();

	void setUserName(String userName);

	String getPassword();
	
	void setPassword(String password);

	String getName();

	void setName(String name);
	
	String getSurname();
	
	void setSurname(String surname);
	
	void setCreationDate(Date date);
	
	void setLastUpdateDate(Date date);
	
	String getFullName();
	
	String getEmail();
	
}
