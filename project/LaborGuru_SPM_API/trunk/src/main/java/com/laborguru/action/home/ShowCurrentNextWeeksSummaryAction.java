/*
 * File name: ShowCurrentWeekSummaryAction.java
 * Creation date: 22/02/2009 10:52:15
 * Copyright Mindpool
 */
package com.laborguru.action.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.PerformanceSummaryRow;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ShowCurrentNextWeeksSummaryAction extends EmployeeHomeSummaryBaseAction {
	private static final Logger log = Logger.getLogger(ShowCurrentNextWeeksSummaryAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 8935610718218090851L;
	private List<PerformanceSummaryRow> nextWeeksSummary;
	
	private static final int NEXT_WEEKS_QTY = 1;
	
	/**
	 * 
	 */
	public ShowCurrentNextWeeksSummaryAction() {
	}


	/**
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		try {
			// Current weeks
			getNextWeeksSummary().add(buildPerformanceSummaryRow(getWeekDaySelector().getStartingWeekDay()));
			
			// Next Weeks
			getWeekDaySelector().setWeeksToShow(NEXT_WEEKS_QTY);
			
			List<Date> days = getWeekDaySelector().getNextStartingWeekDays();
			for(int i = 0; i < days.size(); i++) {
				getNextWeeksSummary().add(buildPerformanceSummaryRow(days.get(i)));
			}
		} catch(Throwable ex) {
			initializeEmptyPastWeeksSummary();
			log.error("Could not retrieve current or next weeks performance summary", ex);
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * 
	 * @param startingWeekDay
	 */
	private PerformanceSummaryRow buildPerformanceSummaryRow(Date startingWeekDay) {
		PerformanceSummaryRow row = new PerformanceSummaryRow();
		
		buildProjectedPerformanceSummaryRow(startingWeekDay, row);
		
		return row;
	}
	
	/**
	 * 
	 */
	private void initializeEmptyPastWeeksSummary() {
		setNextWeeksSummary(null);
		for(int i = 0; i < NEXT_WEEKS_QTY; i++) {
			getNextWeeksSummary().add(new PerformanceSummaryRow());
		}
	}
	
	/**
	 * @return the nextWeeksSummary
	 */
	public List<PerformanceSummaryRow> getNextWeeksSummary() {
		if(nextWeeksSummary == null) {
			setNextWeeksSummary(new ArrayList<PerformanceSummaryRow>(NEXT_WEEKS_QTY));
		}
		return nextWeeksSummary;
	}


	/**
	 * @param nextWeeksSummary the nextWeeksSummary to set
	 */
	public void setNextWeeksSummary(List<PerformanceSummaryRow> nextWeeksSummary) {
		this.nextWeeksSummary = nextWeeksSummary;
	}	

	
}
