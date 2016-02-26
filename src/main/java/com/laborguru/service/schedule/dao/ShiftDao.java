/*
 * File name: ScheduleDao.java
 * Creation date: Oct 4, 2008 4:06:56 PM
 * Copyright Mindpool
 */
package com.laborguru.service.schedule.dao;

import java.util.List;

import com.laborguru.model.Shift;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ShiftDao {

	/**
	 * 
	 * @param contiguousShift
	 * @return
	 */
	Shift getReferencedByShift(Shift contiguousShift);
	
	/**
	 * 
	 * @param shift
	 * @return
	 */
	Shift save(Shift shift);
	
	/**
	 * @return
	 */
	List<Shift> loadAll();
}
