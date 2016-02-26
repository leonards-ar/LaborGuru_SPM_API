/*
 * File name: ShiftServiceBean.java
 * Creation date: 02/02/2009 13:59:26
 * Copyright Mindpool
 */
package com.laborguru.service.schedule;

import java.util.List;

import com.laborguru.model.Shift;
import com.laborguru.service.schedule.dao.ShiftDao;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ShiftServiceBean implements ShiftService {
	private ShiftDao shiftDao;
	
	/**
	 * 
	 */
	public ShiftServiceBean() {
	}

	/**
	 * @param contiguousShift
	 * @return
	 * @see com.laborguru.service.schedule.ShiftService#getReferencedByShift(com.laborguru.model.Shift)
	 */
	public Shift getReferencedByShift(Shift contiguousShift) {
		return getShiftDao().getReferencedByShift(contiguousShift);
	}

	/**
	 * @param shift
	 * @return
	 * @see com.laborguru.service.schedule.ShiftService#save(com.laborguru.model.Shift)
	 */
	public Shift save(Shift shift) {
		return getShiftDao().save(shift);
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.schedule.ShiftService#updateAll()
	 */
	public List<Shift> updateAll() {
		List<Shift> shifts = getShiftDao().loadAll();
		
		for(Shift shift: shifts) {
			getShiftDao().save(shift);
		}
		
		return shifts;
	}
	
	/**
	 * @return the shiftDao
	 */
	public ShiftDao getShiftDao() {
		return shiftDao;
	}

	/**
	 * @param shiftDao the shiftDao to set
	 */
	public void setShiftDao(ShiftDao shiftDao) {
		this.shiftDao = shiftDao;
	}

}
