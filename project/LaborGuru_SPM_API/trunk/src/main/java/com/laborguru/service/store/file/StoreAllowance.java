package com.laborguru.service.store.file;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.InvalidFieldUploadFileException;
import com.laborguru.model.DayOfWeekData;
import com.laborguru.model.DayPart;
import com.laborguru.model.DayPartData;
import com.laborguru.model.Position;
import com.laborguru.model.Store;
import com.laborguru.util.PoiUtils;


/**
 * Represents the Store Allowances Section from the store definition upload file
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class StoreAllowance extends BaseStoreSection{

	private static final Logger log = Logger.getLogger(StoreAllowance.class);
	
	public enum StoreAllowanceField{
		
		WEEKDAY_GUEST_SERVICE("Weekday guest service"),
		WEEKEND_GUEST_SERVICE("Weekend guest service"),
		VARIABLE_FLEXIBLE("Variable Flexible"),
		VARIABLE_OPENING("Variable Opening"),
		FIXED_GUEST_SERVICE("Fixed Guest Service"),		
		FIXED_FLEXIBLE("Fixed flexible"),		
		FIXED_OPENING("Fixed opening"),		
		FIXED_POST_RUSH("Fixed post rush"),		
		FIXED_CLOSING("Fixed closing"),		
		VARIABLE_2_OPENING("Variable 2 Opening"),
		VARIABLE_2_FLEXIBLE("Variable 2 Flexible"),
		VARIABLE_3_OPENING("Variable 3 Opening"),
		VARIABLE_3_FLEXIBLE("Variable 3 Flexible"),
		VARIABLE_4_OPENING("Variable 4 Opening"),
		VARIABLE_4_FLEXIBLE("Variable 4 Flexible");
		
		private String fieldName;
				
		StoreAllowanceField(String fieldName){
			this.fieldName = fieldName;
		}
		
		public String getFieldName(){
			return this.fieldName;
		}
		
		public static StoreAllowanceField getFielType(String fieldName){
			for (StoreAllowanceField field: EnumSet.allOf(StoreAllowanceField.class)){
				if (field.getFieldName().equalsIgnoreCase(fieldName)){
					return field;
				}
			}
			
			String message = "Store allowances row is invalid  - fieldName:"+fieldName;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, ErrorEnum.STORE_INVALID_ROW, new String[] {fieldName});
		}
	}
	
	//DayPartData Mapping values
	private PositionValueMap weekdayGuestService = new PositionValueMap();
	private PositionValueMap weekendGuestService = new PositionValueMap();
	private PositionValueMap variableFlexible = new PositionValueMap();
	private PositionValueMap variableOpening = new PositionValueMap();
	private PositionValueMap fixedGuestService = new PositionValueMap();

	//DayOfWeekData Mapping values
	private PositionValueMap fixedFlexible = new PositionValueMap();
	private PositionValueMap fixedOpening = new PositionValueMap();
	private PositionValueMap fixedPostRush = new PositionValueMap();
	private PositionValueMap fixedClosing = new PositionValueMap();

	private PositionValueMap secondVarsOpenFlex = new PositionValueMap();
	
	/**
	 * Default Constructor
	 */
	public StoreAllowance(){
		super();
		setSection(StoreSection.STORE_ALLOWANCES);
	}
	
	/**
	 * @param fieldName
	 * @param fieldValue
	 */
	@Override
	public void addRowToSection(Row row) {	
				
		String category = PoiUtils.getStringValue(row.getCell((short)1));
		
		StoreAllowanceField fieldType = StoreAllowanceField.getFielType(category);
		
		switch(fieldType){		
			case WEEKDAY_GUEST_SERVICE:
				addPositionValueRow(row, this.weekdayGuestService, fieldType);
				break;
			case WEEKEND_GUEST_SERVICE:
				addPositionValueRow(row, this.weekendGuestService, fieldType);
				break;
			case VARIABLE_FLEXIBLE:
				addPositionValueRow(row, this.variableFlexible, fieldType);
				break;	
			case VARIABLE_OPENING:
				addPositionValueRow(row, this.variableOpening, fieldType);
				break;	
			case FIXED_GUEST_SERVICE:
				addPositionValueRow(row, this.fixedGuestService, fieldType);
				break;	
			case FIXED_FLEXIBLE:
				addPositionValueRow(row, this.fixedFlexible, fieldType);
				break;
			case FIXED_OPENING:
				addPositionValueRow(row, this.fixedOpening, fieldType);
				break;
			case FIXED_POST_RUSH:
				addPositionValueRow(row, this.fixedPostRush, fieldType);
				break;
			case FIXED_CLOSING:
				addPositionValueRow(row, this.fixedClosing, fieldType);
				break;
			case VARIABLE_2_FLEXIBLE:
			case VARIABLE_2_OPENING:
			case VARIABLE_3_FLEXIBLE:
			case VARIABLE_3_OPENING:
			case VARIABLE_4_FLEXIBLE:
			case VARIABLE_4_OPENING:	
				addSecondVarOpenFlexRow(row, this.secondVarsOpenFlex, fieldType);
				break;
			default: 
				throw new IllegalArgumentException("The type passed as parameter is wrong");			
		}
	}


	/**
	 * @param row
	 * @param positionValueMap
	 * @param category
	 */
	private void addPositionValueRow(Row row, PositionValueMap positionValueMap, StoreAllowanceField category) {
		String position = PoiUtils.getStringValue(row.getCell((short)2));
		String field = PoiUtils.getStringValue(row.getCell((short)3));
		Double value = PoiUtils.getDoubleValue(row.getCell((short)4));
	
		if ((position == null) || (value == null) || (field == null)){
			String message = getSection().getStoreSection()+" row is invalid - category: "+category.getFieldName()
			+" - position:"+position +" - field2:"+field + " - value:"+value;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {getSection().getStoreSection(), 
					category.getFieldName()});
		}
		
		positionValueMap.put(position, field, value);		
	}

	
	/**
	 * @param row
	 * @param positionValueMap
	 * @param category
	 */
	private void addSecondVarOpenFlexRow(Row row, PositionValueMap positionValueMap, StoreAllowanceField category) {
		String position = PoiUtils.getStringValue(row.getCell((short)2));
		Double value = PoiUtils.getDoubleValue(row.getCell((short)4));
	
		if ((position == null) || (value == null)){
			String message = getSection().getStoreSection()+" row is invalid - category: "+category.getFieldName()
			+" - position:"+position +"- value:"+value;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {getSection().getStoreSection(), 
					category.getFieldName()});
		}
		
		positionValueMap.put(position, category.getFieldName(), value);		
	}	
	/**
	 * @param store
	 */
	public void assembleStore(Store store) {
		
		validatePositions(store);
		
		for(Position position: store.getPositions()){
			
			String positionName = position.getName();
			for (DayPartData dayPartData : position.getDayPartData()){
				assembleDayPartData(dayPartData, positionName, this.weekdayGuestService, StoreAllowanceField.WEEKDAY_GUEST_SERVICE);
				assembleDayPartData(dayPartData, positionName, this.weekendGuestService, StoreAllowanceField.WEEKEND_GUEST_SERVICE);
				assembleDayPartData(dayPartData, positionName, this.variableFlexible, StoreAllowanceField.VARIABLE_FLEXIBLE);
				assembleDayPartData(dayPartData, positionName, this.variableOpening, StoreAllowanceField.VARIABLE_OPENING);
				assembleDayPartData(dayPartData, positionName, this.fixedGuestService, StoreAllowanceField.FIXED_GUEST_SERVICE);				
			}
			
			for (DayOfWeekData dayOfWeekData : position.getDayOfWeekData()){
				assembleDayOfWeekData(dayOfWeekData, positionName, this.fixedFlexible, StoreAllowanceField.FIXED_FLEXIBLE);
				assembleDayOfWeekData(dayOfWeekData, positionName, this.fixedOpening, StoreAllowanceField.FIXED_OPENING);
				assembleDayOfWeekData(dayOfWeekData, positionName, this.fixedPostRush, StoreAllowanceField.FIXED_POST_RUSH);
				assembleDayOfWeekData(dayOfWeekData, positionName, this.fixedClosing, StoreAllowanceField.FIXED_CLOSING);
			}
			
			assembleSecondVarOpenFlex(position);
		}		
	}


	/**
	 * @param store
	 */
	private void validatePositions(Store store) {
		Set<String> storePositions = new HashSet<String>(store.getPositions().size());
		
		for(Position position: store.getPositions()){
			storePositions.add(position.getName());
		}
		
		Set<String> storeDayParts = new HashSet<String>(store.getDayParts().size());
		
		for(DayPart dayPart: store.getDayParts()){
			storeDayParts.add(dayPart.getName());
		}		
				
		validatePositionAndDayPartParameter(storePositions, storeDayParts, weekdayGuestService, StoreAllowanceField.WEEKDAY_GUEST_SERVICE.getFieldName());
		validatePositionAndDayPartParameter(storePositions, storeDayParts, weekendGuestService, StoreAllowanceField.WEEKEND_GUEST_SERVICE.getFieldName());
		validatePositionAndDayPartParameter(storePositions, storeDayParts, variableFlexible, StoreAllowanceField.VARIABLE_FLEXIBLE.getFieldName());
		validatePositionAndDayPartParameter(storePositions, storeDayParts, variableOpening, StoreAllowanceField.VARIABLE_OPENING.getFieldName());
		validatePositionAndDayPartParameter(storePositions, storeDayParts, fixedGuestService, StoreAllowanceField.FIXED_GUEST_SERVICE.getFieldName());
		validatePositionError(storePositions, fixedFlexible, StoreAllowanceField.FIXED_FLEXIBLE.getFieldName());
		validatePositionError(storePositions, fixedOpening, StoreAllowanceField.FIXED_OPENING.getFieldName());
		validatePositionError(storePositions, fixedPostRush, StoreAllowanceField.FIXED_POST_RUSH.getFieldName());
		validatePositionError(storePositions, fixedClosing, StoreAllowanceField.FIXED_CLOSING.getFieldName());		
	}
	
	
	/**
	 * @param dayPartData
	 * @param positionName
	 * @param positionMap
	 * @param fieldType
	 */
	private void assembleDayPartData(DayPartData dayPartData, String positionName, PositionValueMap positionMap, StoreAllowanceField fieldType){
		Double value = (Double)positionMap.get(positionName, dayPartData.getDayPart().getName());
		
		if (value == null)
			return;
				
		switch(fieldType){
			case WEEKDAY_GUEST_SERVICE:
				dayPartData.setWeekdayGuestService(value);
				break;
			case WEEKEND_GUEST_SERVICE:
				dayPartData.setWeekendGuestService(value);
				break;
			case VARIABLE_FLEXIBLE:
				dayPartData.setVariableFlexible(value);
				break;
			case VARIABLE_OPENING:
				dayPartData.setVariableOpening(value);
				break;
			case FIXED_GUEST_SERVICE:
				dayPartData.setFixedGuestService(value);
				break;
			default: 
				throw new IllegalArgumentException("The fieldType passed in as parameter is not valid - FieldType:"+fieldType);
		}
	}

	/**
	 * @param dayOfWeekData
	 * @param positionName
	 * @param positionMap
	 * @param fieldType
	 */
	private void assembleDayOfWeekData(DayOfWeekData dayOfWeekData, String positionName, PositionValueMap positionMap, StoreAllowanceField fieldType){
		Double value = (Double)positionMap.get(positionName, UploadWeekDays.getUploadWeekDay(dayOfWeekData.getDayOfWeek()).getDayName());
		
		if (value == null)
			return;
				
		switch(fieldType){
			case FIXED_FLEXIBLE:
				dayOfWeekData.setFixedFlexible(value);
				break;
			case FIXED_OPENING:
				dayOfWeekData.setFixedOpening(value);
				break;
			case FIXED_POST_RUSH:
				dayOfWeekData.setFixedPostRush(value);
				break;
			case FIXED_CLOSING:
				dayOfWeekData.setFixedClosing(value);
				break;				
			default: 
				throw new IllegalArgumentException("The fieldType passed in as parameter is not valid - FieldType:"+fieldType);
		}
	}
	
	/**
	 * @param position
	 */
	private void assembleSecondVarOpenFlex(Position position) {		
		String positionName = position.getName();

		position.setVariable2Flexible(getOpenFlexSecondVarValue(positionName, StoreAllowanceField.VARIABLE_2_FLEXIBLE));
		position.setVariable2Opening(getOpenFlexSecondVarValue(positionName, StoreAllowanceField.VARIABLE_2_OPENING));
		position.setVariable3Flexible(getOpenFlexSecondVarValue(positionName, StoreAllowanceField.VARIABLE_3_FLEXIBLE));
		position.setVariable3Opening(getOpenFlexSecondVarValue(positionName, StoreAllowanceField.VARIABLE_3_OPENING));
		position.setVariable4Flexible(getOpenFlexSecondVarValue(positionName, StoreAllowanceField.VARIABLE_4_FLEXIBLE));
		position.setVariable4Opening(getOpenFlexSecondVarValue(positionName, StoreAllowanceField.VARIABLE_4_OPENING));		
	}
	
	/**
	 * @param positionName
	 * @param fieldType
	 * @return
	 */
	private Double getOpenFlexSecondVarValue(String positionName, StoreAllowanceField fieldType){
		Double value = (Double) this.secondVarsOpenFlex.get(positionName, fieldType.getFieldName());
		return value;
	}
}
