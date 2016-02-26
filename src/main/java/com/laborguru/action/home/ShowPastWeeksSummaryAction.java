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
public class ShowPastWeeksSummaryAction extends EmployeeHomeSummaryBaseAction {
	private static final Logger log = Logger.getLogger(ShowPastWeeksSummaryAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 8935610718218090851L;
	private List<PerformanceSummaryRow> pastWeeksSummary;
	
	private static final int PAST_WEEKS_QTY = 2;
	
	/**
	 * 
	 */
	public ShowPastWeeksSummaryAction() {
	}


	/**
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		try {
			getWeekDaySelector().setWeeksToShow(PAST_WEEKS_QTY);
			
			List<Date> days = getWeekDaySelector().getPreviousStartingWeekDays();
			for(int i = days.size() - 1; i >= 0; i--) {
				getPastWeeksSummary().add(buildPerformanceSummaryRow(days.get(i)));
				
			}
		} catch(Throwable ex) {
			initializeEmptyPastWeeksSummary();
			log.error("Could not retrieve past weeks performance summary", ex);
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
		buildActualPerformanceSummaryRow(startingWeekDay, row);
		
		return row;
	}
	
	/**
	 * 
	 */
	private void initializeEmptyPastWeeksSummary() {
		setPastWeeksSummary(null);
		for(int i = 0; i < PAST_WEEKS_QTY; i++) {
			getPastWeeksSummary().add(new PerformanceSummaryRow());
		}
	}
	
	/**
	 * @return the pastWeeksSummary
	 */
	public List<PerformanceSummaryRow> getPastWeeksSummary() {
		if(pastWeeksSummary == null) {
			setPastWeeksSummary(new ArrayList<PerformanceSummaryRow>(PAST_WEEKS_QTY));
		}
		return pastWeeksSummary;
	}


	/**
	 * @param pastWeeksSummary the pastWeeksSummary to set
	 */
	public void setPastWeeksSummary(List<PerformanceSummaryRow> pastWeeksSummary) {
		this.pastWeeksSummary = pastWeeksSummary;
	}	

	
}
