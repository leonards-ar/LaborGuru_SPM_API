package com.laborguru.action.projection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.frontend.model.ActualValueElement;
import com.laborguru.model.ActualHours;
import com.laborguru.model.DailyHistoricSales;
import com.laborguru.model.DistributionType;
import com.laborguru.model.Store;
import com.laborguru.service.actualhours.ActualHoursService;
import com.laborguru.service.historicsales.HistoricSalesService;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * This action deals with Actual Values.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez</a>
 * @version 1.0
 * @since SPM 1.1
 * 
 */
@SuppressWarnings("serial")
public class ActualValuesPrepareAction extends DailyProjectionBaseAction implements Preparable {

	private List<ActualValueElement> dailyActuals = new ArrayList<ActualValueElement>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	private List<Integer> mainValueBeforeUpdate = new ArrayList<Integer>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	private List<Integer> secondValueBeforeUpdate = new ArrayList<Integer>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	private List<Integer> thirdValueBeforeUpdate = new ArrayList<Integer>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	private List<Integer> fourthValueBeforeUpdate = new ArrayList<Integer>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	
	
	private List<Double> actualHoursBeforeUpdate = new ArrayList<Double>(SpmConstants.DAILY_PROJECTION_PERIOD_DAYS);
	
	
	private Integer totalMainValue = 0;
	private Integer totalSecondValue = 0;
	private Integer totalThirdValue = 0;
	private Integer totalFourthValue = 0;
	private double totalActualHours = 0;

	private Integer totalMainBeforeUpdate = 0;
	private Integer totalSecondBeforeUpdate = 0;
	private Integer totalThirdBeforeUpdate = 0;
	private Integer totalFourthBeforeUpdate = 0;
	private double totalHoursBeforeUpdate= 0;
	
	
	HistoricSalesService historicSalesService;
	ActualHoursService actualHoursService;
	
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
	 * 
	 */
	protected void setupDailyProjectionData() {
		// Force object initialization
		initializeDayWeekSelector(getSelectedDate(), getSelectedDate());		

		clearPageValues();

		// Get calculated projections depending on the projection type
		Store employeeStore = this.getEmployeeStore();
		
		// Set default adjusted values
		for (int i = 0; i < SpmConstants.DAILY_PROJECTION_PERIOD_DAYS; i++) {	
			Date dateToRetrieve = getWeekDaySelector().getWeekDays().get(i);
			DailyHistoricSales dailyHistoricSales = getHistoricSalesService().getDailyHistoricSales(employeeStore, dateToRetrieve);
			
			ActualValueElement actualValue = new ActualValueElement();
			// This calculates all main and secondary variables
			dailyHistoricSales.loadDailyValues();
			
			actualValue.setMainValue(dailyHistoricSales.getDailySalesValue());
			actualValue.setActualVariable2(dailyHistoricSales.getDailyProjectionVariable2());
			actualValue.setActualVariable3(dailyHistoricSales.getDailyProjectionVariable3());
			actualValue.setActualVariable4(dailyHistoricSales.getDailyProjectionVariable4());
			
			actualValue.setDate(dateToRetrieve);

			ActualHours ah = getActualHoursService().getActualHoursByDate(employeeStore, dateToRetrieve);			
			if (ah != null){
				actualValue.setHours(ah.getHours());
			}
						
			getDailyActuals().add(actualValue);
		}
				
		// calculate and set the total
		boolean shouldAllowSave = false;
		
		for (ActualValueElement actualValue : getDailyActuals()) {
			int mainValueDisplay = actualValue.getMainValueToDisplay();
			int secondaryValueDisplay = actualValue.getActualVariable2ToDisplay();
			int thirdValueDisplay = actualValue.getActualVariable3ToDisplay();
			int fourthValueDisplay = actualValue.getActualVariable4ToDisplay();
			
			this.totalMainValue += mainValueDisplay;
			this.totalSecondValue += secondaryValueDisplay;
			this.totalThirdValue += thirdValueDisplay;
			this.totalFourthValue += fourthValueDisplay;
			
			getMainValueBeforeUpdate().add(mainValueDisplay);
			getSecondValueBeforeUpdate().add(secondaryValueDisplay);
			getThirdValueBeforeUpdate().add(thirdValueDisplay);
			getFourthValueBeforeUpdate().add(fourthValueDisplay);
			
			this.totalActualHours += actualValue.getHours();
			getActualHoursBeforeUpdate().add(actualValue.getHours());
			
			//If any of the projections for the weeks is editable 
			//so the page should render the save/calculate bottom
			if (!shouldAllowSave && actualValue.getEditable()){
				shouldAllowSave = true;
			}			
		}
		
		setTotalHoursBeforeUpdate(getTotalActualHours());
		setTotalMainBeforeUpdate(getTotalMainValue());
		setTotalSecondBeforeUpdate(getTotalSecondValue());
		setTotalThirdBeforeUpdate(getTotalThirdValue());
		setTotalFourthBeforeUpdate(getTotalFourthValue());
		setAllowToSaveWeek(shouldAllowSave);		
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
		setupDailyProjectionData();
	}	
	
	/**
	 * 
	 * @see com.laborguru.action.projection.DailyProjectionBaseAction#customSave()
	 */
	@Override
	protected void customSave(){
		saveHistoricSales();		
		saveActualHours();		
	}

	/**
	 * 
	 */
	private void saveHistoricSales() {
		//Saving each projection
		int i=0;
		List<Date> weekDates = getWeekDaySelector().getWeekDays();
		for (ActualValueElement dailyActuals: getDailyActuals()){
			if (dailyActuals.getEditable() && (dailyActuals.getMainValueToDisplay() != getMainValueBeforeUpdate().get(i) ||
					                          (isSecondaryVariablesConfigured(1) && dailyActuals.getActualVariable2ToDisplay() != getSecondValueBeforeUpdate().get(i)) || 
					                          (isSecondaryVariablesConfigured(2) && dailyActuals.getActualVariable3ToDisplay() != getThirdValueBeforeUpdate().get(i)) ||
					                          (isSecondaryVariablesConfigured(3) && dailyActuals.getActualVariable4ToDisplay() != getFourthValueBeforeUpdate().get(i)))) {

				DailyHistoricSales dailyHistoricSales = new DailyHistoricSales();
				dailyHistoricSales.setStore(this.getEmployeeStore());
				dailyHistoricSales.setSalesDate(weekDates.get(i));
				
				dailyHistoricSales = getHistoricSalesService().calculateHistoricSalesStaticProjection(dailyHistoricSales, dailyActuals.getMainValue());

				dailyHistoricSales.setDailyProjectionVariable2(dailyActuals.getActualVariable2());
				dailyHistoricSales.setDailyProjectionVariable3(dailyActuals.getActualVariable3());
				dailyHistoricSales.setDailyProjectionVariable4(dailyActuals.getActualVariable4());
				
				getHistoricSalesService().saveDailyHistoricSales(dailyHistoricSales);
			}
			i++;
		}
	}
	
	/**
	 * 
	 */
	private void saveActualHours() {
		//Saving each projection
		int i=0;
		List<Date> weekDates = getWeekDaySelector().getWeekDays();
		for (ActualValueElement dailyActuals: getDailyActuals()){
			if (dailyActuals.getEditable() && (!dailyActuals.getHours().equals(getActualHoursBeforeUpdate().get(i)))){

				ActualHours ah = new ActualHours();
				ah.setStore(this.getEmployeeStore());
				ah.setDate(weekDates.get(i));
				ah.setHours(dailyActuals.getHours());
				
				getActualHoursService().saveOrUpdate(ah);
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
		if(!DistributionType.STATIC.equals(getEmployeeStore().getDistributionType())){
			addActionError(new ErrorMessage(ErrorEnum.STORE_NOT_STATIC.name()));
			//TODO: CN - This flag should be removed when we found the way to ask from the front if actionErrors is empty.
			setProjectionError(true);			
		}		
	}
	
	/**
	 * Clear the totals for the page
	 */
	private void clearPageValues() {
		setTotalMainValue(0);
		setTotalMainBeforeUpdate(0);
		setTotalSecondValue(0);
		setTotalSecondBeforeUpdate(0);
		setTotalThirdValue(0);
		setTotalThirdBeforeUpdate(0);
		setTotalFourthValue(0);
		setTotalFourthBeforeUpdate(0);
		
		setTotalActualHours(0.0);
		setTotalHoursBeforeUpdate(0.0);
		
		getDailyActuals().clear();
		getMainValueBeforeUpdate().clear();
		getSecondValueBeforeUpdate().clear();
		getThirdValueBeforeUpdate().clear();
		getFourthValueBeforeUpdate().clear();
		getActualHoursBeforeUpdate().clear();
		
		setAllowToSaveWeek(true);
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
	 * @return the totalMainValue
	 */
	public Integer getTotalMainValue() {
		return totalMainValue;
	}

	/**
	 * @param totalMainValue the totalMainValue to set
	 */
	public void setTotalMainValue(Integer totalMainValue) {
		this.totalMainValue = totalMainValue;
	}

	/**
	 * @return the dailyActuals
	 */
	public List<ActualValueElement> getDailyActuals() {
		return dailyActuals;
	}

	/**
	 * @param dailyActuals the dailyActuals to set
	 */
	public void setDailyActuals(List<ActualValueElement> dailyActuals) {
		this.dailyActuals = dailyActuals;
	}

	/**
	 * @return the historicSalesService
	 */
	public HistoricSalesService getHistoricSalesService() {
		return historicSalesService;
	}

	/**
	 * @param historicSalesService the historicSalesService to set
	 */
	public void setHistoricSalesService(HistoricSalesService historicSalesService) {
		this.historicSalesService = historicSalesService;
	}

	/**
	 * @return the mainValueBeforeUpate
	 */
	public List<Integer> getMainValueBeforeUpdate() {
		return mainValueBeforeUpdate;
	}

	/**
	 * @param mainValueBeforeUpate the mainValueBeforeUpate to set
	 */
	public void setMainValueBeforeUpdate(List<Integer> mainValueBeforeUpdate) {
		this.mainValueBeforeUpdate = mainValueBeforeUpdate;
	}

	/**
	 * @return the actualHoursBeforeUpdate
	 */
	public List<Double> getActualHoursBeforeUpdate() {
		return actualHoursBeforeUpdate;
	}

	/**
	 * @param actualHoursBeforeUpdate the actualHoursBeforeUpdate to set
	 */
	public void setActualHoursBeforeUpdate(List<Double> actualHoursBeforeUpdate) {
		this.actualHoursBeforeUpdate = actualHoursBeforeUpdate;
	}

	/**
	 * @return the totalActualHours
	 */
	public double getTotalActualHours() {
		return totalActualHours;
	}

	/**
	 * @param totalActualHours the totalActualHours to set
	 */
	public void setTotalActualHours(double totalActualHours) {
		this.totalActualHours = totalActualHours;
	}

	/**
	 * @return the actualHoursService
	 */
	public ActualHoursService getActualHoursService() {
		return actualHoursService;
	}

	/**
	 * @param actualHoursService the actualHoursService to set
	 */
	public void setActualHoursService(ActualHoursService actualHoursService) {
		this.actualHoursService = actualHoursService;
	}

	/**
	 * @return the totalMainBeforeUpdate
	 */
	public Integer getTotalMainBeforeUpdate() {
		return totalMainBeforeUpdate;
	}

	/**
	 * @param totalMainBeforeUpdate the totalMainBeforeUpdate to set
	 */
	public void setTotalMainBeforeUpdate(Integer totalMainBeforeUpdate) {
		this.totalMainBeforeUpdate = totalMainBeforeUpdate;
	}

	/**
	 * @return the totalActualBeforeUpdate
	 */
	public double getTotalHoursBeforeUpdate() {
		return totalHoursBeforeUpdate;
	}

	/**
	 * @param totalActualBeforeUpdate the totalActualBeforeUpdate to set
	 */
	public void setTotalHoursBeforeUpdate(double totalHoursBeforeUpdate) {
		this.totalHoursBeforeUpdate = totalHoursBeforeUpdate;
	}

	/**
	 * @return the secondValueBeforeUpdate
	 */
	public List<Integer> getSecondValueBeforeUpdate() {
		return secondValueBeforeUpdate;
	}

	/**
	 * @param secondValueBeforeUpdate the secondValueBeforeUpdate to set
	 */
	public void setSecondValueBeforeUpdate(List<Integer> secondValueBeforeUpdate) {
		this.secondValueBeforeUpdate = secondValueBeforeUpdate;
	}

	/**
	 * @return the thirdValueBeforeUpdate
	 */
	public List<Integer> getThirdValueBeforeUpdate() {
		return thirdValueBeforeUpdate;
	}

	/**
	 * @param thirdValueBeforeUpdate the thirdValueBeforeUpdate to set
	 */
	public void setThirdValueBeforeUpdate(List<Integer> thirdValueBeforeUpdate) {
		this.thirdValueBeforeUpdate = thirdValueBeforeUpdate;
	}

	/**
	 * @return the fourthValueBeforeUpdate
	 */
	public List<Integer> getFourthValueBeforeUpdate() {
		return fourthValueBeforeUpdate;
	}

	/**
	 * @param fourthValueBeforeUpdate the fourthValueBeforeUpdate to set
	 */
	public void setFourthValueBeforeUpdate(List<Integer> fourthValueBeforeUpdate) {
		this.fourthValueBeforeUpdate = fourthValueBeforeUpdate;
	}

	/**
	 * @return the totalSecondValue
	 */
	public Integer getTotalSecondValue() {
		return totalSecondValue;
	}

	/**
	 * @param totalSecondValue the totalSecondValue to set
	 */
	public void setTotalSecondValue(Integer totalSecondValue) {
		this.totalSecondValue = totalSecondValue;
	}

	/**
	 * @return the totalThirdValue
	 */
	public Integer getTotalThirdValue() {
		return totalThirdValue;
	}

	/**
	 * @param totalThirdValue the totalThirdValue to set
	 */
	public void setTotalThirdValue(Integer totalThirdValue) {
		this.totalThirdValue = totalThirdValue;
	}

	/**
	 * @return the totalFourthValue
	 */
	public Integer getTotalFourthValue() {
		return totalFourthValue;
	}

	/**
	 * @param totalFourthValue the totalFourthValue to set
	 */
	public void setTotalFourthValue(Integer totalFourthValue) {
		this.totalFourthValue = totalFourthValue;
	}

	/**
	 * @return the totalSecondaryBeforeUpdate
	 */
	public Integer getTotalSecondBeforeUpdate() {
		return totalSecondBeforeUpdate;
	}

	/**
	 * @param totalSecondaryBeforeUpdate the totalSecondaryBeforeUpdate to set
	 */
	public void setTotalSecondBeforeUpdate(Integer totalSecondBeforeUpdate) {
		this.totalSecondBeforeUpdate = totalSecondBeforeUpdate;
	}

	/**
	 * @return the totalThirdBeforeUpdate
	 */
	public Integer getTotalThirdBeforeUpdate() {
		return totalThirdBeforeUpdate;
	}

	/**
	 * @param totalThirdBeforeUpdate the totalThirdBeforeUpdate to set
	 */
	public void setTotalThirdBeforeUpdate(Integer totalThirdBeforeUpdate) {
		this.totalThirdBeforeUpdate = totalThirdBeforeUpdate;
	}

	/**
	 * @return the totalFourthBeforeUpdate
	 */
	public Integer getTotalFourthBeforeUpdate() {
		return totalFourthBeforeUpdate;
	}

	/**
	 * @param totalFourthBeforeUpdate the totalFourthBeforeUpdate to set
	 */
	public void setTotalFourthBeforeUpdate(Integer totalFourthBeforeUpdate) {
		this.totalFourthBeforeUpdate = totalFourthBeforeUpdate;
	}
}
