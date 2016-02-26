/*
 * File name: ScheduleServiceBean.java
 * Creation date: Oct 4, 2008 4:06:28 PM
 * Copyright Mindpool
 */
package com.laborguru.service.schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.laborguru.model.EmployeeSchedule;
import com.laborguru.model.Shift;
import com.laborguru.model.Store;
import com.laborguru.model.StoreSchedule;
import com.laborguru.service.schedule.dao.ScheduleDao;
import com.laborguru.util.CalendarUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleServiceBean implements ScheduleService {
	private ScheduleDao scheduleDao;
	
	/**
	 * 
	 */
	public ScheduleServiceBean() {
	}

	/**
	 * @param store
	 * @param date
	 * @return
	 * @see com.laborguru.service.schedule.ScheduleService#getStoreScheduleByDate(com.laborguru.model.Store, java.util.Date)
	 */
	public StoreSchedule getStoreScheduleByDate(Store store, Date date) {
		return getScheduleDao().getStoreScheduleByDate(store, date);
	}

	/**
	 * @param schedule
	 * @return
	 * @see com.laborguru.service.schedule.ScheduleService#save(com.laborguru.model.StoreSchedule)
	 */
	public StoreSchedule save(StoreSchedule schedule) {
		return getScheduleDao().save(schedule);
	}

	/**
	 * @return the scheduleDao
	 */
	public ScheduleDao getScheduleDao() {
		return scheduleDao;
	}

	/**
	 * @param scheduleDao the scheduleDao to set
	 */
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	/**
	 * 
	 * @param schedule
	 * @return
	 * @see com.laborguru.service.schedule.ScheduleService#detach(com.laborguru.model.StoreSchedule)
	 */
	public StoreSchedule detach(StoreSchedule schedule) {
		return getScheduleDao().detach(schedule);
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.schedule.ScheduleService#getTotalScheduledHoursForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public BigDecimal getTotalScheduledHoursForTimePeriod(Store store,
			Date startDate, Date endDate) {
		return getScheduleDao().getTotalScheduledHoursForTimePeriod(store, startDate, endDate);
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.schedule.ScheduleService#getTotalScheduledLaborCostForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public BigDecimal getTotalScheduledLaborCostForTimePeriod(Store store,
			Date startDate, Date endDate) {
		return getScheduleDao().getTotalScheduledLaborCostForTimePeriod(store, startDate, endDate);
	}

	/**
	 * 
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.laborguru.service.schedule.ScheduleService#getTotalScheduledHoursByPositionForTimePeriod(com.laborguru.model.Store, java.util.Date, java.util.Date)
	 */
	public Map<Integer, BigDecimal> getTotalScheduledHoursByPositionForTimePeriod(
			Store store, Date startDate, Date endDate) {
		return getScheduleDao().getTotalScheduledHoursByPositionForTimePeriod(store, startDate, endDate);
	}
	
	/**
	 * 
	 * 
	 * @see com.laborguru.service.schedule.ScheduleService#updateAllStoreSchedules()
	 */
	public void updateAllStoreSchedules() {
		List<StoreSchedule> schedules = getScheduleDao().getAllStoreSchedules();
		
		for(StoreSchedule aSchedule : schedules) {
			for(EmployeeSchedule employeeSchedule : aSchedule.getEmployeeSchedules()) {
				employeeSchedule.reindexShifts();
			}			
			getScheduleDao().save(aSchedule);
		}
	}

	public StoreSchedule copyScheduleTo(StoreSchedule sourceSchedule, Date date, StoreSchedule destinationNextDaySchedule) {
		StoreSchedule destinationSchedule = getStoreScheduleByDate(sourceSchedule.getStore(), date);
		if(destinationSchedule == null) {
			destinationSchedule = new StoreSchedule();
			destinationSchedule.setDay(date);
			destinationSchedule.setStore(sourceSchedule.getStore());
		}
		
		copyEmployeeSchedule(sourceSchedule, destinationSchedule, destinationNextDaySchedule);
		
		return destinationSchedule;
	}
	
	private void copyEmployeeSchedule(StoreSchedule sourceSchedule, StoreSchedule destinationSchedule, StoreSchedule destinationNextDaySchedule) {
		Iterator<EmployeeSchedule> srcIt = sourceSchedule.getEmployeeSchedules().iterator();
		Iterator<EmployeeSchedule> destIt = prepareEmployeeSchedule(destinationSchedule, sourceSchedule.getEmployeeSchedules().size()).iterator();
		
		destinationSchedule.getEmployeeSchedules().clear();
		
		while(srcIt.hasNext()) {
			EmployeeSchedule src = srcIt.next();
			EmployeeSchedule dest = destIt.next();
			
			dest.setEmployee(src.getEmployee());
			dest.setStoreSchedule(destinationSchedule);
			
			destinationSchedule.getEmployeeSchedules().add(dest);
			
			copyShifts(src, dest, destinationNextDaySchedule != null ? destinationNextDaySchedule.getEmployeeSchedule(src.getEmployee()) : null);
		}
	}

	private void copyShifts(EmployeeSchedule sourceEmployeeSchedule, EmployeeSchedule destinationEmployeeSchedule, EmployeeSchedule destinationNextDayEmployeeSchedule) {
		// Bug SPM#218
		sourceEmployeeSchedule.reindexShifts();
		List<Shift> srcShifts = sourceEmployeeSchedule.getShifts();
		
		prepareShifts(destinationEmployeeSchedule, srcShifts.size());
		
		for(int i = 0; i < srcShifts.size(); i++) {
			Shift src = srcShifts.get(i);
			Shift dest = destinationEmployeeSchedule.getShifts().get(i);
			
			
			// Bug SPM#218
			if(dest == null) {
				dest = new Shift();
				destinationEmployeeSchedule.getShifts().set(i, dest);
			}
			
			dest.setClosingHours(src.getClosingHours());
			dest.setFromHour(src.getFromHour());
			dest.setOpeningHours(src.getOpeningHours());
			dest.setPosition(src.getPosition());
			dest.setServiceHours(src.getServiceHours());
			dest.setToHour(src.getToHour());
			
			if(src.hasContiguousShift() && destinationNextDayEmployeeSchedule != null) {
				Shift candidateShif = destinationNextDayEmployeeSchedule.getFirstShiftFor(src.getPosition());
				if(CalendarUtils.equalsTime(candidateShif.getFromHour(), dest.getToHour())) {
					dest.setContiguousShift(candidateShif);
				}
			}
		}
	}
	
	private void prepareShifts(EmployeeSchedule destinationEmployeeSchedule, int size) {
		int originalSize = destinationEmployeeSchedule.getShifts().size();
		if(size > originalSize) {
			for(int i = 0; i < size - originalSize; i++) {
				destinationEmployeeSchedule.addShift(new Shift());
			}			
		} else if(size < originalSize) {
			for(int i = 0; i < originalSize - size; i++) {
				destinationEmployeeSchedule.removeLastShift();
			}				
		}
	}
	
	private List<EmployeeSchedule> prepareEmployeeSchedule(StoreSchedule destinationSchedule, int size) {
		List<EmployeeSchedule> schedules = new ArrayList<EmployeeSchedule>(size);
		
		int originalSize = destinationSchedule.getEmployeeSchedules().size();
		Iterator<EmployeeSchedule> destIt = destinationSchedule.getEmployeeSchedules().iterator();

		for(int i=0; i < size; i++) {
			if(i < originalSize && destIt.hasNext()) {
				schedules.add(destIt.next());
			} else {
				schedules.add(new EmployeeSchedule());
			}
		}
		
		return schedules;
	}

	public void evict(StoreSchedule schedule) {
		getScheduleDao().evict(schedule);
	}
}
