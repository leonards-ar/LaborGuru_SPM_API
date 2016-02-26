package com.laborguru.service.store.file;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.InvalidFieldUploadFileException;
import com.laborguru.model.DayOfWeek;
import com.laborguru.model.DayOfWeekData;
import com.laborguru.model.DayPart;
import com.laborguru.model.DayPartData;
import com.laborguru.model.DistributionType;
import com.laborguru.model.OperationTime;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.model.StoreVariableDefinition;
import com.laborguru.util.PoiUtils;


/**
 * Represents a Store Operation Section from the store definition upload file
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreOperation extends BaseStoreSection {

	private static final Logger log = Logger.getLogger(StoreOperation.class);

	private static final String FIRST_DAY_OF_WEEK = "First day of week";
	private static final String OPEN = "Open";
	private static final String CLOSE = "Close";
	private static final String OPENING_EXTRA_HOURS = "Opening extra hours";
	private static final String CLOSING_EXTRA_HOURS = "Closing extra hours";
	
	//Projections Defaults
	//TODO:Include this on the upload file
	private static final Integer DAILY_PROJECTION_WEEK_DEFAULT = 4;
	private static final Integer HALF_HOUR_PROJECTION_WEEK_DEFAULT = 4;
	
	public enum StoreOperationField{
		
		HOURS_OF_OPERATION("Hours of operation"),
		DAYPART_DEFINITION("Daypart definition"),
		POSITION_NAMES("Position names"),
		GROUP_NAMES("Group names"),		
		VARIABLE_DEFINITION("Variable definition"),
		GUEST_SERVICE("Guest Service"),
		DISTRIBUTION_TYPE("Distribution Type"),
		MANAGER("Manager");
		
		private String fieldName;
				
		StoreOperationField(String fieldName){
			this.fieldName = fieldName;
		}
		
		public String getFieldName(){
			return this.fieldName;
		}
		
		/**
		 * @param fieldName
		 * @return
		 */
		public static StoreOperationField getFielType(String fieldName){
			for (StoreOperationField field: EnumSet.allOf(StoreOperationField.class)){
				if (field.getFieldName().equalsIgnoreCase(fieldName)){
					return field;
				}
			}
			
			String message = "Store Information row is invalid  - fieldName:"+fieldName;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, ErrorEnum.STORE_INVALID_ROW, new String[] {fieldName});
		}
	}
	
	private OperationTime[] hoursOfOperation = new OperationTime[7];
	private List<DayPart> dayPartDefinitions = new ArrayList<DayPart>();
	private Set<String> positionNames = new HashSet<String>();
	private Set<String> groupNames = new HashSet<String>();
	private List<String> variableDefinitions = new ArrayList<String>();
	private Set<String> managerSet = new HashSet<String>();
	private Set<String> guestServiceSet = new HashSet<String>();
	private DayOfWeek firstDayOfWeek;	
	private DistributionType distributionType;
	
	/**
	 * Default Constructor
	 */
	public StoreOperation(){
		super();
		setSection(StoreSection.STORE_OPERATIONS);
	}
	
	/**
	 * @param row
	 * @see com.laborguru.service.store.file.BaseStoreSection#addRowToSection(org.apache.poi.hssf.usermodel.Row)
	 */
	@Override
	protected void addRowToSection(Row row) {	
				
		String category = PoiUtils.getStringValue(row.getCell((short)1));
		
		StoreOperationField infoStoreType = StoreOperationField.getFielType(category);
		
		switch(infoStoreType){		
			case HOURS_OF_OPERATION:
				addHoursOfOperation(row);
				break;
			case DAYPART_DEFINITION:
				addDaypartDefinition(row);
				break;
			case POSITION_NAMES:
				addPositionNames(row);
				break;
			case GROUP_NAMES:
				addGroupNames(row);
				break;
			case VARIABLE_DEFINITION:
				addVariableDefinitions(row);
				break;
			case MANAGER:
				addManager(row);
				break;
			case GUEST_SERVICE:
				addGuestService(row);
				break;
			case DISTRIBUTION_TYPE:
				addDitributionType(row);
				break;
			default: throw new IllegalArgumentException("The type passed as parameter is wrong");			
		}
	}
	
	/**
	 * @param row
	 * @see com.laborguru.service.store.file.BaseStoreSection#validateRow(org.apache.poi.hssf.usermodel.Row)
	 */
	@Override
	protected void validateRow(Row row){
		Cell category = row.getCell((short)1);
		Cell fieldValue = row.getCell((short)4);
		
		if ((category == null) || (fieldValue == null)){
			String message = getSection().getStoreSection()+" row is invalid - category:"+category+" - fieldValue:"+fieldValue;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, ErrorEnum.STORE_INVALID_ROW, new String[] {getSection().getStoreSection()});
		}
	}
	
	/**
	 * @param row
	 */
	private void addVariableDefinitions(Row row){
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));
		getVariableDefinitions().add(fieldValue.trim());
	}
	
	/**
	 * @param row
	 */
	private void addGroupNames(Row row) {
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));		
		getGroupNames().add(fieldValue.trim());
	}

	/**
	 * @param row
	 */
	private void addPositionNames(Row row) {
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));
		getPositionNames().add(fieldValue.trim());
		
	}

	/**
	 * @param row
	 */
	private void addGuestService(Row row) {
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));
		getGuestServiceSet().add(fieldValue.trim());

	}

	/**
	 * @param row
	 */
	private void addManager(Row row) {
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));
		getManagerSet().add(fieldValue.trim());
	}	
		
	/**
	 * @param row
	 */
	private void addDaypartDefinition(Row row) {		
		String fieldName = PoiUtils.getStringValue(row.getCell((short)2));
		Date startTime = PoiUtils.getDateValue(row.getCell((short)4));

		if (fieldName == null || startTime == null){
			String message = getSection().getStoreSection()+" row is invalid - category: day part definition - fieldName:"+fieldName + " - startTime:"+startTime;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {getSection().getStoreSection(), 
					StoreOperationField.DAYPART_DEFINITION.getFieldName()});
		}

		DayPart dayPart = new DayPart();
		dayPart.setName(fieldName.trim());
		dayPart.setStartHour(startTime);
		
		getDayPartDefinitions().add(dayPart);
	}

	/**
	 * @param fieldName
	 * @param fieldAux
	 * @param fieldValue
	 */
	private void addHoursOfOperation(Row row) {
		
		String fieldName = PoiUtils.getStringValue(row.getCell((short)2));
				
		if (FIRST_DAY_OF_WEEK.equalsIgnoreCase(fieldName)){
			String fieldValue = row.getCell((short)4).getStringCellValue();
			UploadWeekDays firstDay = UploadWeekDays.getUploadWeekDay(fieldValue);
			
			if (firstDay!=null){
				setFirstDayOfWeek(firstDay.getDayOfWeek());
			}
			
			return;
		}
		
		String fieldAux = PoiUtils.getStringValue(row.getCell((short)3));		
		OperationTime operationTime = getHourOfOperation(fieldName);
		
		if(OPENING_EXTRA_HOURS.equalsIgnoreCase(fieldAux)) {
			operationTime.setOpeningExtraHours(PoiUtils.getIntegerValue(row.getCell((short)4)));
			return;
		} else if(CLOSING_EXTRA_HOURS.equalsIgnoreCase(fieldAux)) {
			operationTime.setClosingExtraHours(PoiUtils.getIntegerValue(row.getCell((short)4)));
			return;
		}
		
		// This means that just operation time is left as possibility
		Date hour = PoiUtils.getDateValue(row.getCell((short)4));
		
		if ((hour == null) || (operationTime == null) ){
			String message = getSection().getStoreSection()+" row is invalid - category: Hour of operation - fieldName:"+fieldName + " - fieldAux:"+fieldAux+ " - hour: "+ hour;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {getSection().getStoreSection(), 
					StoreOperationField.HOURS_OF_OPERATION.getFieldName()});
		}
		
		if (OPEN.equalsIgnoreCase(fieldAux)){
			operationTime.setOpenHour(hour);
		}else if (CLOSE.equalsIgnoreCase(fieldAux)){
			operationTime.setCloseHour(hour);
		} else {
			String message = getSection().getStoreSection()+" row is invalid - category: Hour of operation - fieldName:"+fieldName + " - fieldAux:"+fieldAux+ " - hour: "+ hour;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {getSection().getStoreSection(), 
					StoreOperationField.HOURS_OF_OPERATION.getFieldName()});
		}
	}


	/**
	 * @param fieldName
	 * @return
	 */
	private OperationTime getHourOfOperation(String fieldName) {

		UploadWeekDays dayOfWeek = UploadWeekDays.getUploadWeekDay(fieldName);
		
		if (dayOfWeek == null){
			return null;
		}
		
		if (getHoursOfOperation()[dayOfWeek.ordinal()] == null){
			OperationTime operationTime = new OperationTime();
			operationTime.setDayOfWeek(dayOfWeek.getDayOfWeek());
			getHoursOfOperation()[dayOfWeek.ordinal()] = operationTime;
		}
		return getHoursOfOperation()[dayOfWeek.ordinal()];
	}

	/**
	 * @param row
	 */
	private void addDitributionType(Row row) {
		String fieldValue = PoiUtils.getStringValue(row.getCell((short)4));
		
		if(DistributionType.STATIC.name().equalsIgnoreCase(fieldValue)){
			setDistributionType(DistributionType.STATIC);
		}else{
			setDistributionType(DistributionType.HISTORIC_AVG);			
		}		
	}
	
	/**
	 * @param store
	 */
	public void assembleStore(Store store) {
		
		validatePositions();
		
		for (OperationTime operationTime: getHoursOfOperation()){
			if (operationTime != null){
				store.addOperationTime(operationTime);
			}
		}
		
		int index = 0;		
		for (DayPart dayPart: getDayPartDefinitions()){
			dayPart.setPositionIndex(index++);
			store.addDayPart(dayPart);
		}

		index=0;
		for (String positionName: getPositionNames()){
			Position position = new Position();
			position.setPositionIndex(index++);
			position.setName(positionName);
			
			boolean isManager = getManagerSet().contains(positionName);
			position.setManager(isManager);
			
			boolean isGuestService = getGuestServiceSet().contains(positionName);
			position.setGuestService(isGuestService);
			
			//Building the dayparts data
			for (DayPart dayPart: store.getDayParts()){
				DayPartData dayPartData = new DayPartData();
				dayPartData.setDayPart(dayPart);
				dayPartData.setPosition(position);
				
				position.getDayPartData().add(dayPartData);
			}			

			//Building the day of week data
			for (DayOfWeek dayOfWeek : EnumSet.allOf(DayOfWeek.class)){
				DayOfWeekData data = new DayOfWeekData();
				data.setDayOfWeek(dayOfWeek);
				data.setPosition(position);
				
				position.getDayOfWeekData().add(data);
			}
			
			store.addPosition(position);
		}

		//Setting the groups
		for (String groupName: getGroupNames()){
			PositionGroup positionGroup = new PositionGroup();
			positionGroup.setName(groupName);
			store.addPositionGroup(positionGroup);
		}
		
		//Setting first day of the week
		if (getFirstDayOfWeek() != null){
			store.setFirstDayOfWeek(getFirstDayOfWeek());
		}

		//Setting default projection values
		store.setDailyProjectionsWeeksDefault(DAILY_PROJECTION_WEEK_DEFAULT);
		store.setHalfHourProjectionsWeeksDefault(HALF_HOUR_PROJECTION_WEEK_DEFAULT);
		
		//Setting variable definition
		int k=0;
		for(String variable: getVariableDefinitions()){
			StoreVariableDefinition variableDefinition = new StoreVariableDefinition();
			variableDefinition.setName(variable);
			variableDefinition.setVariableIndex(k);
			store.addVariableDefinition(variableDefinition);
			k++;
		}
		
		//Setting distribution type
		if (getDistributionType() != null){
			store.setDistributionType(getDistributionType());
		}
	}

	
	private void validatePositions(){
		validatePositionError(getPositionNames(), getManagerSet(), StoreOperationField.MANAGER.getFieldName());
		validatePositionError(getPositionNames(), getGuestServiceSet(), StoreOperationField.GUEST_SERVICE.getFieldName());		
	}
	
	
	/**
	 * @return the hoursOfOperation
	 */
	public OperationTime[] getHoursOfOperation() {
		return hoursOfOperation;
	}


	/**
	 * @return the dayPartDefinitions
	 */
	public List<DayPart> getDayPartDefinitions() {
		return dayPartDefinitions;
	}

	/**
	 * @param dayPartDefinitions the dayPartDefinitions to set
	 */
	public void setDayPartDefinitions(List<DayPart> dayPartDefinitions) {
		this.dayPartDefinitions = dayPartDefinitions;
	}

	/**
	 * @return the positionNames
	 */
	public Set<String> getPositionNames() {
		return positionNames;
	}

	/**
	 * @param positionNames the positionNames to set
	 */
	public void setPositionNames(Set<String> positionNames) {
		this.positionNames = positionNames;
	}

	/**
	 * @return the groupNames
	 */
	public Set<String> getGroupNames() {
		return groupNames;
	}

	/**
	 * @param groupNames the groupNames to set
	 */
	public void setGroupNames(Set<String> groupNames) {
		this.groupNames = groupNames;
	}

	/**
	 * @return the variableDefinitions
	 */
	public List<String> getVariableDefinitions() {
		return variableDefinitions;
	}

	/**
	 * @param variableDefinitions the variableDefinitions to set
	 */
	public void setVariableDefinitions(List<String> variableDefinitions) {
		this.variableDefinitions = variableDefinitions;
	}

	/**
	 * @return the firstDayOfWeek
	 */
	public DayOfWeek getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	/**
	 * @param firstDayOfWeek the firstDayOfWeek to set
	 */
	public void setFirstDayOfWeek(DayOfWeek firstDayOfWeek) {
		this.firstDayOfWeek = firstDayOfWeek;
	}

	/**
	 * @return the managerList
	 */
	public Set<String> getManagerSet() {
		return managerSet;
	}

	/**
	 * @return the guestServiceList
	 */
	public Set<String> getGuestServiceSet() {
		return guestServiceSet;
	}

	/**
	 * @return the distributionType
	 */
	private DistributionType getDistributionType() {
		return distributionType;
	}

	/**
	 * @param distributionType the distributionType to set
	 */
	private void setDistributionType(DistributionType distributionType) {
		this.distributionType = distributionType;
	}
}
