/*
 * File name: ShiftDaoHibernate.java
 * Creation date: 02/02/2009 14:07:24
 * Copyright Mindpool
 */
package com.laborguru.service.schedule.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.laborguru.model.Shift;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ShiftDaoHibernate extends HibernateDaoSupport implements ShiftDao {
	private static final Logger log = Logger.getLogger(ShiftDaoHibernate.class);	
	
	/**
	 * 
	 */
	public ShiftDaoHibernate() {
	}

	/**
	 * @param contiguousShift
	 * @return
	 * @see com.laborguru.service.schedule.dao.ShiftDao#getReferencedByShift(com.laborguru.model.Shift)
	 */
	public Shift getReferencedByShift(Shift contiguousShift) {
		if(log.isDebugEnabled()) {
			log.debug("Searching shifts that have as contiguous shift [" + contiguousShift + "]");
		}
		
		List<Shift> shifts = (List<Shift>) getHibernateTemplate().findByNamedParam("from Shift shift where shift.contiguousShift.id = :shiftId", new String[]{"shiftId"}, new Object[] {contiguousShift.getId()});
		
		if(log.isDebugEnabled()) {
			log.debug("Found [" + (shifts != null ? shifts.size() : "null") + "] shifts for contiguous shift [" + contiguousShift + "]");
		}
		return shifts != null && shifts.size() > 0 ? shifts.get(0) : null;
	}

	/**
	 * @param shift
	 * @return
	 * @see com.laborguru.service.schedule.dao.ShiftDao#save(com.laborguru.model.Shift)
	 */
	public Shift save(Shift shift) {
		if (shift == null){
			throw new IllegalArgumentException("The shift passed as parameter is null");
		}
		
		getHibernateTemplate().saveOrUpdate(shift);
		
		return shift;
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.schedule.dao.ShiftDao#loadAll()
	 */
	public List<Shift> loadAll() {
		return (List<Shift>)getHibernateTemplate().loadAll(Shift.class);
	}

}
