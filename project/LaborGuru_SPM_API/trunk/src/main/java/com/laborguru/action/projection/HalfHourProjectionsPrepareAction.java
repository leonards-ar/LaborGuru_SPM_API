package com.laborguru.action.projection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.frontend.model.HalfHourElement;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.HalfHourProjection;
import com.laborguru.model.OperationTime;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * This action deals with Half Hours Projections.
 * 
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
@SuppressWarnings("serial")
public class HalfHourProjectionsPrepareAction extends ProjectionCalendarBaseAction implements Preparable {
	
	private List<HalfHourElement> projectionElements = new ArrayList<HalfHourElement>();
	private BigDecimal totalProjectedValues = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	private BigDecimal totalAdjustedValues = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	private BigDecimal totalRevisedValues = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);

	private OperationTime storeOperationTime;
	
	/**
	 * Returns whether the page is editable
	 * @return
	 */
	public boolean isEditable() {
		if(System.getProperty(SpmConstants.DEBUG) != null) {
			return true;
		} else {
			return ((!getWeekDaySelector().isSelectedDateBeforeToday()) && (getTotalProjectedValues().doubleValue() > 0.00));
		}
	}
	
	/**
	 * Returns the operation times for this store
	 * @return
	 */
	public OperationTime getStoreOperationTime() {
		if (this.storeOperationTime == null){
			this.storeOperationTime = super.getEmployeeStore().getOperationTime(getWeekDaySelector().getSelectedDayOfWeek());
		}
		return this.storeOperationTime;
	}
	
	
	/**
	 * Returns whether the total for adjusted values is greater than zero
	 * @return
	 */
	public boolean isTotalRevisedValuesGreaterThanZero(){
		return this.getTotalRevisedValues().doubleValue() > 0.00;
	}
	
	/**
	 * Returns whether the halfhour should be showed.
	 * @param halfHour
	 * @return
	 */
	public boolean isHalfHourVisible(HalfHourElement halfHour){
		Date time = CalendarUtils.displayTimeToDate(halfHour.getHour());
		OperationTime auxOperationTime = getStoreOperationTime();
		
		if (!CalendarUtils.inRangeNotIncludingEndTime(time, auxOperationTime.getOpenHour(), auxOperationTime.getCloseHour())) {
			return (halfHour.getProjectedValue().doubleValue() > 0.00);
		}
		
		return true;
	}
	
	/**
	 * Prepare the data to be used on the edit page
	 * 
	 * @throws Exception
	 */
	public void prepareEdit() {
		pageSetup();
	}

	/**
	 * Prepare the data to be used on the add page
	 * 
	 */
	public void prepareSave() {
		pageSetup();
	}

	/**
	 *  Prepare the data to be used on the revise used weeks result page (edit page)
	 * 
	 */
	public void prepareReviseUsedWeeks(){
		pageSetup();
	}
	
	/**
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#prepareChangeDay()
	 */
	@Override
	public void prepareChangeDay() {
		pageSetup();
	}

	/**
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#prepareChangeWeek()
	 */
	@Override
	public void prepareChangeWeek() {
		pageSetup();
	}

	/**
	 * 
	 */
	public void prepareReviseProjections() {
		pageSetup();
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
	 * 
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#processChangeDay()
	 */
	@Override
	protected void processChangeDay() {
		setUsedWeeks(this.getEmployeeStore().getDailyProjectionsWeeksDefault());
		setUpHalfHourProjection();
	}

	/**
	 * 
	 * 
	 * @see com.laborguru.action.projection.ProjectionCalendarBaseAction#processChangeWeek()
	 */
	@Override
	protected void processChangeWeek() {
		setUsedWeeks(this.getEmployeeStore().getDailyProjectionsWeeksDefault());
		setUpHalfHourProjection();
	}	
	
	
	/**
	 * Prepares the edit page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		//Calendar initialization
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		setUpHalfHourProjection();
		
		setSelectedDate(getWeekDaySelector().getStringStartingWeekDay());
		setSelectedWeekDay(getWeekDaySelector().getStringSelectedDay());
		
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		//Calendar initialization
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());

		getProjectionService().updateHalfHourProjection(this.getEmployeeStore(), getElementsAsHalfHourProjectionList(getProjectionElements()), getWeekDaySelector().getSelectedDay());

		edit();
		
		return SpmActionResult.EDIT.getResult();
	}
	
	public String reviseProjections() {
		//Calendar initialization
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
				
		calculateAndSetReviseProjections();
		
		return SpmActionResult.EDIT.getResult();
	}

	public String reviseUsedWeeks() {
		//Calendar initialization
		initializeDayWeekSelector(getSelectedDate(), getSelectedWeekDay());
		
		getNewValues();
		
		return SpmActionResult.EDIT.getResult();		
	}
	
	/**
	 * 
	 */
	private void getNewValues(){
		
		//Setting the date for the calculation
		DayOfWeek auxSelectedDOW = getWeekDaySelector().getSelectedDayOfWeek();
		
		Date currentStartWeekDate = CalendarUtils.getDayOfThisWeek(getWeekDaySelector().getStartingWeekDay()).getTime();		
		Date calculatedDate = CalendarUtils.getDayOfTheWeek(currentStartWeekDate, auxSelectedDOW); 
		
		BigDecimal auxTotal = getTotalProjectedValues();
		
		if (getTotalProjectedValues().equals(SpmConstants.BD_ZERO_VALUE)){
			DailyProjection dailyProjection = getProjectionService().getDailyProjection(getEmployeeStore(), getWeekDaySelector().getSelectedDay());
			auxTotal = dailyProjection.getDailyProjectionValue();
		}
		
		List<HalfHourProjection> projections = getProjectionService().calculateHistoricHalfHourProjections(getEmployeeStore(), auxTotal, calculatedDate, getUsedWeeks());
		BigDecimal totalProjections = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);

		//Get the new total;
		for(int i=0; i < getProjectionElements().size(); i++) {
			totalProjections = totalProjections.add(projections.get(i).getAdjustedValue());
		}
		
		//If the new total is zero we show an error page
		if (totalProjections.equals(SpmConstants.BD_ZERO_VALUE)){
			addActionError(new ErrorMessage(ErrorEnum.NO_PROJECTION_VALUES_FOR_SELECTED_WEEKS.name()));
			return;
		}
		
		//setting new values;
		for(int i=0; i < getProjectionElements().size(); i++) {
			getProjectionElements().get(i).setProjectedValue(projections.get(i).getAdjustedValue());
		}		
		
		setTotalProjectedValues(totalProjections);
		
		//if there is adjusted values calculate the revised projections
		if(getTotalAdjustedValues().doubleValue() > 0.00) {
			calculateAndSetReviseProjections();
		}
	}

	/**
	 * Setup the the projections element and the projected total from the daily projection
	 */
	private void setUpHalfHourProjection() {
		
		//clear Totals
		clearTotalPageValues();
				
		DailyProjection dailyProjection = getProjectionService().getDailyProjection(getEmployeeStore(), getWeekDaySelector().getSelectedDay());

		if (dailyProjection != null) {	
			//Setting projection element array
			setProjectionElements(getHalfHourElementList(dailyProjection.getHalfHourProjections()));
			
			//Setting total value
			BigDecimal auxTotal = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
			for (HalfHourElement element: getProjectionElements()){
				auxTotal = auxTotal.add(element.getProjectedValue());
			}			
			setTotalProjectedValues(auxTotal);
		}else {
			addActionError(new ErrorMessage(ErrorEnum.PROJECTION_DOES_NOT_EXIST.name()));
			//TODO: CN - This flag should be removed when we found the way to ask from the front if actionErrors is empty.
			setProjectionError(true);
		}
	}	
	
	/**
	 * Clear the totals for the page
	 */
	private void clearTotalPageValues() {
		totalProjectedValues = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		totalAdjustedValues = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		totalRevisedValues = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
	}
	
	/**
	 * Private method that calculates and sets the revised projections values for the collection ProjectionElements.
	 */
	private void calculateAndSetReviseProjections() {
		
		BigDecimal totalAdjusted = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal totalProjections = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal percentageNotChangedHours = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal totalOriginalAdjustedProjections = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		BigDecimal totalRevised = new BigDecimal(SpmConstants.INIT_VALUE_ZERO);
		
		//Getting totals for Adjusted and Projected values		
		for (HalfHourElement element : getProjectionElements()) {
			if (element.getAdjustedValue() != null) {
				totalAdjusted = totalAdjusted.add(element.getAdjustedValue());
				totalOriginalAdjustedProjections = totalOriginalAdjustedProjections.add(element.getProjectedValue());
			}
			
			totalProjections = totalProjections.add(element.getProjectedValue());
		}
		
		if(totalAdjusted.compareTo(totalProjections) > 0){
			addActionError(new ErrorMessage(ErrorEnum.CHANGES_BIGGER_THAN_PROJECTION.name()));
			setProjectionError(true);
			return;
		}
		
		//Calculating percentageNotChangedHours
		percentageNotChangedHours = totalProjections.subtract(totalOriginalAdjustedProjections);
		percentageNotChangedHours = percentageNotChangedHours.divide(totalProjections, SpmConstants.DECIMAL_SCALE, SpmConstants.ROUNDING_MODE);
		
		//Calculating revised projection value
		if (!percentageNotChangedHours.equals(SpmConstants.BD_ZERO_VALUE)){
			for (HalfHourElement element : getProjectionElements()) {
				getProjectionService().calculateRevisedValue(element, totalAdjusted, totalProjections, percentageNotChangedHours);
				totalRevised = totalRevised.add(element.getRevisedValue());
			}
		}
		setTotalRevisedValues(totalRevised);
		setTotalAdjustedValues(totalAdjusted);
		setTotalProjectedValues(totalProjections);
	}
		

	/**
	 * @param halfHourProjections
	 * @param startTime
	 */
	private List<HalfHourElement>  getHalfHourElementList(List<HalfHourProjection> halfHourProjections){

		if (halfHourProjections == null)
			return null;
		
		List<HalfHourElement> arrayList = new ArrayList<HalfHourElement>(halfHourProjections.size());
		
		for (HalfHourProjection halfHourProjection : halfHourProjections) {
			HalfHourElement element = new HalfHourElement();
			element.setId(halfHourProjection.getId());
			element.setHour(halfHourProjection.getTimeAsString());
			element.setProjectedValue(halfHourProjection.getAdjustedValue());
			element.setRevisedValue(BigDecimal.ZERO);
			arrayList.add(element);
		}
		
		return arrayList;
	}	
	
	/**
	 * @param projectionElements2 TODO
	 * @return
	 */
	private List<HalfHourProjection> getElementsAsHalfHourProjectionList(List<HalfHourElement> auxElements) {
		
		List<HalfHourProjection> retList = new ArrayList<HalfHourProjection>(auxElements.size());
		
		int index=0;
		for (HalfHourElement element : auxElements) {
			HalfHourProjection aHalfHourProjection = new HalfHourProjection();
			aHalfHourProjection.setIndex(index++);
			aHalfHourProjection.setTime(element.getHour());
			aHalfHourProjection.setAdjustedValue(element.getRevisedValue());
			retList.add(aHalfHourProjection);
		}
		
		return retList;
	}
	
	/**
	 * @return the projectionElements
	 */
	public List<HalfHourElement> getProjectionElements() {
		return projectionElements;
	}

	/**
	 * @param projectionElements
	 *            the projectionElements to set
	 */
	public void setProjectionElements(List<HalfHourElement> projectionElements) {
		this.projectionElements = projectionElements;
	}

	/**
	 * @return the totalProjectedValues
	 */
	public BigDecimal getTotalProjectedValues() {
		return totalProjectedValues;
	}

	/**
	 * @param totalProjectedValues the totalProjectedValues to set
	 */
	public void setTotalProjectedValues(BigDecimal totalProjectedValues) {
		this.totalProjectedValues = totalProjectedValues;
	}

	/**
	 * @return the totalAdjustedValues
	 */
	public BigDecimal getTotalAdjustedValues() {
		return totalAdjustedValues;
	}

	/**
	 * @param totalAdjustedValues the totalAdjustedValues to set
	 */
	public void setTotalAdjustedValues(BigDecimal totalAdjustedValues) {
		this.totalAdjustedValues = totalAdjustedValues;
	}

	/**
	 * @return the totalRevisedValues
	 */
	public BigDecimal getTotalRevisedValues() {
		return totalRevisedValues;
	}

	/**
	 * @param totalRevisedValues the totalRevisedValues to set
	 */
	public void setTotalRevisedValues(BigDecimal totalRevisedValues) {
		this.totalRevisedValues = totalRevisedValues;
	}
}
