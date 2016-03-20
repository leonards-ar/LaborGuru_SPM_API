/**
 * 
 */
package com.laborguru.service.menu.dao;

import java.util.List;

import com.laborguru.service.dao.hibernate.SpmHibernateDao;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.laborguru.model.MenuItem;

/**
 * @author Mariano
 *
 */
public class MenuDaoHibernate extends SpmHibernateDao implements MenuDao {

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
		return (List<MenuItem>) getSession().createQuery("from MenuItem item where item.parentMenuItem = null order by item.position").list();
	}

}
