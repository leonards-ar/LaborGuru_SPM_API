package com.laborguru.action.projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.frontend.model.DailyProjectionElement;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.DistributionType;
import com.laborguru.model.Store;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * This action deals with Daily Projections.
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
@SuppressWarnings("serial")
public class DailyProjectionsPrepareAction extends DailyProjectionBaseAction implements Preparable {

	private List<DailyProjectionElement> dailyProjections = new ArrayList<DailyProjectionElement>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	
	//The totals are only used for presentation.
	//The real values for each daily projection (not Integer if not BigDecimal) are kept in dailyProjections list.
	private Integer totalProjected = 0;
	private Integer totalAdjusted = 0;
	private Integer totalVariable2 = 0;
	private Integer totalVariable3 = 0;
	private Integer totalVariable4= 0;

	//Flag that indicates wheter the projections view allows to save a new projection to the user.
	//By default is true, the value is set in setupDailyProjectionData()
	private Boolean allowToSaveWeek = true;
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#prepareChangeDay()
	 */
	@Override
	public void prepareChangeDay() {
		//It's need it as it was declared abstract in the super class. We are not using change day for daily projection behaviour.
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#prepareChangeWeek()
	 */
	@Override
	public void prepareChangeWeek() {
		prepareEdit();
	}
	
	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		// It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * Clear the totals for the page
	 */
	private void clearPageValues() {
		
		setTotalAdjusted(0);
		setTotalProjected(0);
		
		setTotalVariable2(0);
		setTotalVariable3(0);
		setTotalVariable4(0);
		
		getDailyProjections().clear();
		setAllowToSaveWeek(true);
	}
		
	
	/**
	 * 
	 */
	protected void setupDailyProjectionData() {		
		// Force object initialization
		initializeDayWeekSelector(getSelectedDate(), getSelectedDate());		

		clearPageValues();

		Store employeeStore = this.getEmployeeStore();

		//Setting the date for the weekly calculation
		Date calculatedDate = getWeekDaySelector().getStartingWeekDay();
		
		if (!DistributionType.STATIC.equals(employeeStore.getDistributionType()))
		{
			Date firstDayThisWeek =  getWeekDaySelector().getFirstDayOfWeek(CalendarUtils.todayWithoutTime());
	
			if (calculatedDate.compareTo(firstDayThisWeek) >= 0){
				calculatedDate = firstDayThisWeek;			
			}
		}
				
		// Get calculated projections		
		List<BigDecimal> calculatedProjections = getProjectionService().calculateWeeklyProjectionValues(getUsedWeeks(), employeeStore, calculatedDate);
		
		List<DailyProjection> adjustedProjections = getProjectionService().getAdjustedDailyProjectionForAWeek(employeeStore, getWeekDaySelector().getStartingWeekDay());

		Calendar  auxCalendar = Calendar.getInstance();
		// Set default adjusted values
		for (int i = 0; i < SpmConstants.DAILY_PROJECTION_PERIOD_DAYS; i++) {
			DailyProjectionElement dailyProjection = new DailyProjectionElement();
			
			dailyProjection.setAdjustedProjection(adjustedProjections.get(i).getDailyProjectionValue());			
			dailyProjection.setProjectionVariable2(adjustedProjections.get(i).getDailyProjectionVariable2());
			dailyProjection.setProjectionVariable3(adjustedProjections.get(i).getDailyProjectionVariable3());			
			dailyProjection.setProjectionVariable4(adjustedProjections.get(i).getDailyProjectionVariable4());
			
			dailyProjection.setProjectionDate(getWeekDaySelector().getWeekDays().get(i));
			
			auxCalendar.setTime(dailyProjection.getProjectionDate());
			BigDecimal auxValue = calculatedProjections.get(i);
			
			dailyProjection.setCalculatedProjection(auxValue);
			
			if (dailyProjection.getAdjustedProjection() == null)
				dailyProjection.setAdjustedProjection(auxValue);
			
			getDailyProjections().add(dailyProjection);
		}

				
		// calculate and set the total
		boolean shouldAllowSave = false;
		for (DailyProjectionElement projection : getDailyProjections()) {
			this.totalProjected += NumberUtils.bigDecimalToInt(projection.getCalculatedProjection());
			this.totalAdjusted += NumberUtils.bigDecimalToInt(projection.getAdjustedProjection());
			this.totalVariable2 += NumberUtils.bigDecimalToInt(projection.getProjectionVariable2());
			this.totalVariable3 += NumberUtils.bigDecimalToInt(projection.getProjectionVariable3());
			this.totalVariable4 += NumberUtils.bigDecimalToInt(projection.getProjectionVariable4());
			
			//If any of the projections for the weeks is editable 
			//so the page should render the save/calculate bottom
			if (!shouldAllowSave && projection.getEditable()){
				shouldAllowSave = true;
			}
		}
		setAllowToSaveWeek(shouldAllowSave);
	}

	
	/**
	 * 
	 * @see com.laborguru.action.projection.DailyProjectionBaseAction#customSave()
	 */
	@Override
	protected void customSave() {
		//Saving each projection
		int i=0;
		List<Date> weekDates = getWeekDaySelector().getWeekDays();
		Date currentStartWeekDate = CalendarUtils.getDayOfThisWeek(weekDates.get(0)).getTime();
		for (DailyProjectionElement dailyProjection: getDailyProjections()){
			if (dailyProjection.getEditable()){			
				Date calculatedDate = CalendarUtils.addOrSubstractDays(currentStartWeekDate, i);
				
				DailyProjection dailyProjectionToUpdate = dailyProjection.createDailyProjection();
				dailyProjectionToUpdate.setStore(this.getEmployeeStore());
				dailyProjectionToUpdate.setProjectionDate(weekDates.get(i));
				
				getProjectionService().calculateAndSaveDailyProjection(dailyProjectionToUpdate, dailyProjection.getAdjustedProjection(),calculatedDate);
			}
			i++;
		}		
	}

	/**
	 * 
	 * @see com.laborguru.action.projection.DailyProjectionBaseAction#customEdit()
	 */
	@Override
	protected void customEdit() {
		//Nothing todo
	}
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#processChangeDay()
	 */
	@Override
	protected void processChangeDay() {
		//It's need it as it was declared abstract in the super class. We are not using change day for daily projection behaviour.
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#processChangeWeek()
	 */
	@Override
	protected void processChangeWeek() {
		setUsedWeeks(this.getEmployeeStore().getDailyProjectionsWeeksDefault());
		setupDailyProjectionData();
	}	
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		setupDailyProjectionData();

		return SpmActionResult.EDIT.getResult();
	}


	/**
	 * @return the totalProjected
	 */
	public Integer getTotalProjected() {
		return totalProjected;
	}

	/**
	 * @param totalProjected
	 *            the totalProjected to set
	 */
	public void setTotalProjected(Integer totalProjected) {
		this.totalProjected = totalProjected;
	}

	/**
	 * @return the totalAdjusted
	 */
	public Integer getTotalAdjusted() {
		return totalAdjusted;
	}

	/**
	 * @param totalAdjusted
	 *            the totalAdjusted to set
	 */
	public void setTotalAdjusted(Integer totalAdjusted) {
		this.totalAdjusted = totalAdjusted;
	}

	/**
	 * @return the dailyProjections
	 */
	public List<DailyProjectionElement> getDailyProjections() {
		return dailyProjections;
	}

	/**
	 * @param dailyProjections the dailyProjections to set
	 */
	public void setDailyProjections(List<DailyProjectionElement> dailyProjections) {
		this.dailyProjections = dailyProjections;
	}

	/**
	 * @return the allowToSave
	 */
	public Boolean getAllowToSaveWeek() {
		return allowToSaveWeek;
	}

	/**
	 * @param allowToSave the allowToSave to set
	 */
	public void setAllowToSaveWeek(Boolean allowToSave) {
		this.allowToSaveWeek = allowToSave;
	}

	/**
	 * @return the totalVariable2
	 */
	public Integer getTotalVariable2() {
		return totalVariable2;
	}

	/**
	 * Set the totalVariable2
	 */
	public void setTotalVariable2(Integer total) {
		this.totalVariable2 = total;
	}
	
	/**
	 * @return the totalVariable3
	 */
	public Integer getTotalVariable3() {
		return totalVariable3;
	}

	/**
	 * @return the totalVariable4
	 */
	public Integer getTotalVariable4() {
		return totalVariable4;
	}

	/**
	 * @param totalVariable3 the totalVariable3 to set
	 */
	public void setTotalVariable3(Integer totalVariable3) {
		this.totalVariable3 = totalVariable3;
	}

	/**
	 * @param totalVariable4 the totalVariable4 to set
	 */
	public void setTotalVariable4(Integer totalVariable4) {
		this.totalVariable4 = totalVariable4;
	}	
}
