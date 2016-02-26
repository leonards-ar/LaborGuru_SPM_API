package com.laborguru.service.profile.dao;

import java.util.List;

import com.laborguru.model.Profile;
import com.laborguru.service.dao.hibernate.SpmHibernateDao;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ProfileDaoHibernate extends SpmHibernateDao implements ProfileDao {

	public List<Profile> findAll() {
		return (List<Profile>)getHibernateTemplate().loadAll(Profile.class);
	}

}
