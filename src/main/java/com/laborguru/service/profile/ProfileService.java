package com.laborguru.service.profile;

import java.util.List;

import com.laborguru.model.Profile;
import com.laborguru.service.Service;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ProfileService extends Service {

	/**
	 * Gets all the profiles
	 * @return
	 */
	List<Profile> findAll();
		
}
