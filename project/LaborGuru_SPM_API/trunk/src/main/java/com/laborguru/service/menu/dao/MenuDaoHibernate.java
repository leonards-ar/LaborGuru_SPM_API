/**
 * 
 */
package com.laborguru.service.menu.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.MenuItem;

/**
 * @author Mariano
 *
 */
public class MenuDaoHibernate extends HibernateDaoSupport implements MenuDao {

	/**
	 * 
	 */
	public MenuDaoHibernate() {

	}

	/**
	 * 
	 * @see com.laborguru.service.menu.dao.MenuDao#getMenuhItemsFor(com.laborguru.model.User)
	 */
	public List<MenuItem> getMenu() {
		return (List<MenuItem>) getHibernateTemplate().find("from MenuItem item where item.parentMenuItem = null order by item.position");
	}

}
