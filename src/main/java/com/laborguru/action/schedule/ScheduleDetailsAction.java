/*
 * File name: ScheduleDetailsAction.java
 * Creation date: Nov 27, 2008 11:06:46 AM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.ScheduleDetailRow;
import com.laborguru.frontend.model.WeekDaySelector;
import com.laborguru.model.DailyStaffing;
import com.laborguru.model.HalfHourStaffing;
import com.laborguru.model.Position;
import com.laborguru.model.StoreDailyStaffing;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ScheduleDetailsAction extends ScheduleBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7865174211531869913L;
	
	private Position position;
	private boolean completeDetails;
	private String selectedDay;
	private boolean weekly;
	
	private List<ScheduleDetailRow> hourDetails;
	private Double opening;
	private Double closing;
	private Double postRush;
	private Double flexible;
	private Double total;
	private Double guestService;
	
	private StaffingService staffingService;
	
	/**
	 * 
	 */
	public ScheduleDetailsAction() {
	}

	/**
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		if(isWeekly()) {
			
		} else {
			setDailyDetails();
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * 
	 */
	private void setDailyDetails() {
		StoreDailyStaffing dailyStaffing = getStaffingService().getDailyStaffingByDate(getEmployeeStore(), getSelectedDayAsDate());
		if(getPosition() == null || getPosition().getId() == null) {
			setDailyDetailsForAllPositions(dailyStaffing);
		} else {
			setDailyDetailsForPosition(dailyStaffing);
		}
		setGuestService(new Double(getTotal().doubleValue() - getOpening().doubleValue() - getClosing().doubleValue()));		
	}
	
	/**
	 * 
	 * @param dailyStaffing
	 */
	private void setDailyDetailsForAllPositions(StoreDailyStaffing dailyStaffing) {
		setTotal(dailyStaffing.getTotalDailyTarget());
		setOpening(dailyStaffing.getTotalOpening());
		setClosing(dailyStaffing.getTotalClosing());
		setPostRush(dailyStaffing.getTotalPostRush());
		setFlexible(dailyStaffing.getTotalFlexible());
		
		Date closeTime = getStoreCloseTime();
		
		for(Date time = getStoreOpenTime(); CalendarUtils.equalsOrSmallerTime(time, closeTime); time = CalendarUtils.addOrSubstractMinutes(time, SpmConstants.HALF_HOUR)) {
			ScheduleDetailRow aRow = new ScheduleDetailRow();
			
			aRow.setHour(time);
			aRow.setValue(dailyStaffing.getTotalMinimumStaffingFor(time));
			
			getHourDetails().add(aRow);
		}		
	}

	/**
	 * 
	 * @return
	 */
	private Date getStoreCloseTime() {
		return getStoreScheduleEndHour(getSelectedDayAsDate());
	}
	
	/**
	 * 
	 * @return
	 */
	private Date getStoreOpenTime() {
		return getStoreScheduleStartHour(getSelectedDayAsDate());
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isStoreOpen(Date time) {
		return CalendarUtils.equalsOrGreaterTime(time, getStoreScheduleStartHour(getSelectedDayAsDate())) &&  CalendarUtils.equalsOrSmallerTime(time, getStoreScheduleEndHour(getSelectedDayAsDate()));
	}
	
	/**
	 * 
	 * @param dailyStaffing
	 */
	private void setDailyDetailsForPosition(StoreDailyStaffing dailyStaffing) {
		DailyStaffing positionDailyStaffing = dailyStaffing.getDailyStaffingFor(getPosition());
		setTotal(positionDailyStaffing.getTotalDailyTarget());
		setOpening(positionDailyStaffing.getTotalOpening());
		setClosing(positionDailyStaffing.getFixedClosing());
		setPostRush(positionDailyStaffing.getFixedPostRush());
		setFlexible(positionDailyStaffing.getTotalFlexible());
		
		for(HalfHourStaffing halfHourStaffing : positionDailyStaffing.getHalfHourStaffing()) {
			if(isStoreOpen(halfHourStaffing.getTime())) {
				ScheduleDetailRow aRow = new ScheduleDetailRow();
				
				aRow.setHour(halfHourStaffing.getTime());
				aRow.setValue(halfHourStaffing.getCalculatedStaff());
				
				getHourDetails().add(aRow);
			}
		}
	}
	
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the completeDetails
	 */
	public boolean isCompleteDetails() {
		return completeDetails;
	}

	/**
	 * @param completeDetails the completeDetails to set
	 */
	public void setCompleteDetails(boolean completeDetails) {
		this.completeDetails = completeDetails;
	}

	/**
	 * @return the selectedDay
	 */
	public String getSelectedDay() {
		return selectedDay;
	}

	/**
	 * 
	 * @return
	 */
	public Date getSelectedDayAsDate() {
		return WeekDaySelector.getAsDate(getSelectedDay());
	}
	
	/**
	 * @param selectedDay the selectedDay to set
	 */
	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
	}

	/**
	 * @return the weekly
	 */
	public boolean isWeekly() {
		return weekly;
	}

	/**
	 * @param weekly the weekly to set
	 */
	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	/**
	 * @return the hourDetails
	 */
	public List<ScheduleDetailRow> getHourDetails() {
		if(hourDetails == null) {
			setHourDetails(new ArrayList<ScheduleDetailRow>());
		}
		return hourDetails;
	}

	/**
	 * @param hourDetails the hourDetails to set
	 */
	public void setHourDetails(List<ScheduleDetailRow> hourDetails) {
		this.hourDetails = hourDetails;
	}

	/**
	 * @return the opening
	 */
	public Double getOpening() {
		if(opening == null) {
			setOpening(new Double(0.0));
		}
		return opening;
	}

	/**
	 * @param opening the opening to set
	 */
	public void setOpening(Double opening) {
		this.opening = opening;
	}

	/**
	 * @return the closing
	 */
	public Double getClosing() {
		if(closing == null) {
			setClosing(new Double(0.0));
		}
		return closing;
	}

	/**
	 * @param closing the closing to set
	 */
	public void setClosing(Double closing) {
		this.closing = closing;
	}

	/**
	 * @return the postRush
	 */
	public Double getPostRush() {
		if(postRush == null) {
			setPostRush(new Double(0.0));
		}
		return postRush;
	}

	/**
	 * @param postRush the postRush to set
	 */
	public void setPostRush(Double postRush) {
		this.postRush = postRush;
	}

	/**
	 * @return the flexible
	 */
	public Double getFlexible() {
		if(flexible == null) {
			setFlexible(new Double(0.0));
		}
		return flexible;
	}

	/**
	 * @param flexible the flexible to set
	 */
	public void setFlexible(Double flexible) {
		this.flexible = flexible;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		if(total == null) {
			setTotal(new Double(0.0));
		}
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the guestService
	 */
	public Double getGuestService() {
		if(guestService == null) {
			setGuestService(new Double(0.0));
		}
		return guestService;
	}

	/**
	 * @param guestService the guestService to set
	 */
	public void setGuestService(Double guestService) {
		this.guestService = guestService;
	}

	/**
	 * @return the staffingService
	 */
	public StaffingService getStaffingService() {
		return staffingService;
	}

	/**
	 * @param staffingService the staffingService to set
	 */
	public void setStaffingService(StaffingService staffingService) {
		this.staffingService = staffingService;
	}
}
