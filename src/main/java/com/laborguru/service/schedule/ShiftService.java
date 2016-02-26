/*
 * File name: ScheduleService.java
 * Creation date: Oct 4, 2008 3:56:19 PM
 * Copyright Mindpool
 */
package com.laborguru.service.schedule;

import java.util.List;

import com.laborguru.model.Shift;
import com.laborguru.service.Service;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public interface ShiftService extends Service {

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
	List<Shift> updateAll();
}
 