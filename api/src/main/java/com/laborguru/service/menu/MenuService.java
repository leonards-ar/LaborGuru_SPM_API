/**
 * 
 */
package com.laborguru.service.menu;

import com.laborguru.model.Menu;
import com.laborguru.model.User;
import com.laborguru.service.Service;

/**
 * @author Mariano
 *
 */
public interface MenuService extends Service {

	/**
	 * Builds the menu for the current user. Just take into
	 * accounts the menu items the user has permission to.
	 * @param user The user
	 * @return The menu for the user
	 */
	Menu getMenuFor(User user);
}
