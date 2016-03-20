/**
 * 
 */
package com.laborguru.service.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.laborguru.model.Menu;
import com.laborguru.model.MenuItem;
import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.service.menu.dao.MenuDao;

/**
 * @author Mariano
 *
 */
public class MenuServiceBean implements MenuService {
	private static final Map<String, Menu> MENU_CACHE = new HashMap<String, Menu>();
	
	private MenuDao menuDao;

	/**
	 * 
	 */
	public MenuServiceBean() {
		
	}
	
	/**
	 * @return the dao
	 */
	public MenuDao getMenuDao() {
		return menuDao;
	}

	/**
	 * @param menuDao the dao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/**
	 * @see com.laborguru.service.menu.MenuService#getMenuFor(com.laborguru.model.User)
	 */
	public Menu getMenuFor(User user) {
		/*
		 * :TODO: Must support multiple profiles per user!
		 * :TODO: Use ehcache for the MENU_CACHE so it can be refreshed
		 */
		String pk = getPrimaryKey(user.getProfiles());
		Menu menu = MENU_CACHE.get(pk);
		if(menu == null) {
			List<MenuItem> completeMenu = getMenuDao().getMenu();
			removeNotAllowedMenuItems(completeMenu, user.getProfiles());
			menu = new Menu();
			menu.setItems(completeMenu);
			MENU_CACHE.put(pk, menu);
		}
		return menu;
	}

	/**
	 * 
	 * @param menuItems
	 */
	private void removeNotAllowedMenuItems(Collection<MenuItem> menuItems, Set<Profile> userProfiles) {
		if(menuItems != null) {
			List<MenuItem> itemsToRemove = new ArrayList<MenuItem>();
			
			for(MenuItem item : menuItems) {
				boolean hasPermission = false;
				for(Profile profile : userProfiles) {
					if(profile.hasPermission(item.getPermission())) {
						hasPermission = true;
						break;
					}
				}
				if(!hasPermission) {
					itemsToRemove.add(item);
				}
				removeNotAllowedMenuItems(item.getChildMenuItems(), userProfiles);
			}
			menuItems.removeAll(itemsToRemove);
		}
	}
	
	private String getPrimaryKey(Set<Profile> profiles) {
		StringBuilder primaryKey = new StringBuilder();
		for(Profile profile: profiles){
			primaryKey.append(profile.getId());
		}
		return primaryKey.toString();
	}
}
