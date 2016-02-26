package com.laborguru.service.store.file;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.InvalidFieldUploadFileException;
import com.laborguru.model.DayPart;
import com.laborguru.model.DayPartData;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.Store;
import com.laborguru.util.PoiUtils;

/**
 * Represents a Labor Assumption Section from the store definition upload file
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 * 
 */
public class LaborAssumption extends BaseStoreSection {

	private static final Logger log = Logger.getLogger(LaborAssumption.class);

	private static final String BOTTOM_UTILIZATION = "Bottom";
	private static final String TOP_UTILIZATION = "Top";
	private static final String NON_GUEST_SERV_UTL = "NON-GUEST SERVICE UTL.";

	private static final String MINIMUM_UTILIZATION = "Minimum";
	private static final String MAXIMUM_UTILIZATION = "Maximum";

	public enum LaborAssumptionField {

		OTHERS_FACTORS("Other factors"), UTILIZATION("Utilization"), UTILIZATION_LIMITS(
				"Utilization limits"), MINIMUM_STAFFING("Minimum staffing"), ACTIVITY_SHARING(
				"Activity Sharing");

		private String fieldName;

		LaborAssumptionField(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName() {
			return this.fieldName;
		}

		public static LaborAssumptionField getFielType(String fieldName) {
			for (LaborAssumptionField field : EnumSet
					.allOf(LaborAssumptionField.class)) {
				if (field.getFieldName().equalsIgnoreCase(fieldName)) {
					return field;
				}
			}

			String message = "LaborAssumption row is invalid  - fieldName:"
					+ fieldName;
			log.error(message);
			throw new InvalidFieldUploadFileException(message,
					ErrorEnum.STORE_INVALID_ROW, new String[] { fieldName });
		}
	}

	public enum OtherFactorsField {

		SCHEDULE_INEFFICENCY("SCHEDULE INEFFICENCY"), FILL_INEFFICIENCY(
				"FILL INEFFICIENCY"), TRAINING_FACTOR("TRAINING FACTOR"), EARNED_BREAKS_FACTOR(
				"EARNED BREAKS FACTOR"), FLOOR_MANAGEMENT_FACTOR(
				"FLOOR MANAGEMENT FACTOR"), MINIMUM_FLOOR_MANAGEMENT_HOURS(
				"MINIMUM FLOOR MANAGEMENT HOURS");

		private String factorName;

		OtherFactorsField(String factorName) {
			this.factorName = factorName;
		}

		public String getFactorName() {
			return this.factorName;
		}
	}

	private Map<String, Double> otherFactors = new HashMap<String, Double>(6);
	private Map<String, Double> utilizationBottom = new HashMap<String, Double>();
	private Map<String, Double> utilizationTop = new HashMap<String, Double>();
	private Double nonGuestServiceUtilization;

	private Map<String, Integer> utilizationLimitsMin = new HashMap<String, Integer>();
	private Map<String, Integer> utilizationLimitsMax = new HashMap<String, Integer>();

	private PositionValueMap minimumStaffing = new PositionValueMap();

	private Map<String, String> activitySharing = new HashMap<String, String>();

	/**
	 * Default Constructor
	 */
	public LaborAssumption() {
		super();
		setSection(StoreSection.LABOR_ASSUMPTIONS);
	}

	/**
	 * @param fieldName
	 * @param fieldValue
	 */
	@Override
	public void addRowToSection(Row row) {

		String category = PoiUtils.getStringValue(row.getCell((short) 1));

		LaborAssumptionField fieldType = LaborAssumptionField
				.getFielType(category);

		switch (fieldType) {
		case OTHERS_FACTORS:
			addOtherFactors(row);
			break;
		case ACTIVITY_SHARING:
			addActivitySharing(row);
			break;
		case MINIMUM_STAFFING:
			addMinimumStaffing(row);
			break;
		case UTILIZATION:
			addUtilization(row);
			break;
		case UTILIZATION_LIMITS:
			addUtilizationLimits(row);
			break;
		default:
			throw new IllegalArgumentException(
					"The type passed as parameter is wrong");
		}
	}

	/**
	 * @param row
	 */
	private void addActivitySharing(Row row) {
		String position = PoiUtils.getStringValue(row.getCell((short) 2));
		String group = PoiUtils.getStringValue(row.getCell((short) 4));

		if ((position == null) || (group == null)) {
			String message = getSection().getStoreSection()
					+ " row is invalid - category: "
					+ LaborAssumptionField.ACTIVITY_SHARING.getFieldName()
					+ " - position:" + position + " - group:" + group;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {
					getSection().getStoreSection(),
					LaborAssumptionField.ACTIVITY_SHARING.getFieldName() });
		}

		activitySharing.put(position, group);

	}

	/**
	 * @param row
	 */
	private void addMinimumStaffing(Row row) {
		String position = PoiUtils.getStringValue(row.getCell((short) 2));
		String dayPart = PoiUtils.getStringValue(row.getCell((short) 3));
		Integer value = PoiUtils.getIntegerValue(row.getCell((short) 4));

		if ((position == null) || (value == null) || (dayPart == null)) {
			String message = getSection().getStoreSection()
					+ " row is invalid - category: "
					+ LaborAssumptionField.MINIMUM_STAFFING.getFieldName()
					+ " - position:" + position + " - dayPart:" + dayPart
					+ " - value:" + value;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {
					getSection().getStoreSection(),
					LaborAssumptionField.MINIMUM_STAFFING.getFieldName() });
		}

		minimumStaffing.put(position, dayPart, value);
	}

	/**
	 * @param row
	 */
	private void addOtherFactors(Row row) {
		String fieldName = PoiUtils.getStringValue(row.getCell((short) 2));
		Double factor = PoiUtils.getDoubleValue(row.getCell((short) 4));

		if (fieldName == null || factor == null) {
			String message = getSection().getStoreSection()
					+ " row is invalid - category: Other Factors - fieldName:"
					+ fieldName + " - factor:" + factor;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {
					getSection().getStoreSection(),
					LaborAssumptionField.OTHERS_FACTORS.getFieldName() });
		}

		getOtherFactors().put(fieldName, factor);
	}

	/**
	 * @param row
	 */
	private void addUtilization(Row row) {
		String fieldName = PoiUtils.getStringValue(row.getCell((short) 2));
		String position = PoiUtils.getStringValue(row.getCell((short) 3));
		Double value = PoiUtils.getDoubleValue(row.getCell((short) 4));

		if (!areUtilizationFieldsValid(fieldName, position, value)) {
			String message = getSection().getStoreSection()
					+ " row is invalid - category: "
					+ LaborAssumptionField.UTILIZATION.getFieldName()
					+ " - fieldName:" + fieldName + " - position:" + position
					+ " - factor:" + value;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {
					getSection().getStoreSection(),
					LaborAssumptionField.UTILIZATION.getFieldName() });
		}

		if (TOP_UTILIZATION.equalsIgnoreCase(fieldName)) {
			getUtilizationTop().put(position, value);
		} else if (BOTTOM_UTILIZATION.equalsIgnoreCase(fieldName)) {
			getUtilizationBottom().put(position, value);
		} else {
			setNonGuestServiceUtilization(value);
		}
	}

	/**
	 * @param fieldName
	 * @param position
	 * @param value
	 * @return
	 */
	private boolean areUtilizationFieldsValid(String fieldName,
			String position, Double value) {

		if ((fieldName == null) || (value == null)) {
			return false;
		}

		if (TOP_UTILIZATION.equalsIgnoreCase(fieldName)
				|| BOTTOM_UTILIZATION.equalsIgnoreCase(fieldName)) {
			return position != null;
		}

		if (NON_GUEST_SERV_UTL.equalsIgnoreCase(fieldName)) {
			return (value >= 0);
		}

		return false;
	}

	/**
	 * @param row
	 */
	private void addUtilizationLimits(Row row) {
		String fieldName = PoiUtils.getStringValue(row.getCell((short) 2));
		String position = PoiUtils.getStringValue(row.getCell((short) 3));
		Integer value = PoiUtils.getIntegerValue(row.getCell((short) 4));

		if (!areUtilizationLimitsFieldsValid(fieldName, position, value)) {
			String message = getSection().getStoreSection()
					+ " row is invalid - category: "
					+ LaborAssumptionField.UTILIZATION_LIMITS.getFieldName()
					+ " - fieldName:" + fieldName + " - position:" + position
					+ " - factor:" + value;
			log.error(message);
			throw new InvalidFieldUploadFileException(message, new String[] {
					getSection().getStoreSection(),
					LaborAssumptionField.UTILIZATION_LIMITS.getFieldName() });
		}

		if (MAXIMUM_UTILIZATION.equalsIgnoreCase(fieldName)) {
			getUtilizationLimitsMax().put(position, value);
		} else if (MINIMUM_UTILIZATION.equalsIgnoreCase(fieldName)) {
			getUtilizationLimitsMin().put(position, value);
		}
	}

	/**
	 * @param fieldName
	 * @param position
	 * @param value
	 * @return
	 */
	private boolean areUtilizationLimitsFieldsValid(String fieldName,
			String position, Integer value) {

		if ((fieldName == null) || (value == null) || (position == null)) {
			return false;
		}

		return (MAXIMUM_UTILIZATION.equalsIgnoreCase(fieldName) || MINIMUM_UTILIZATION
				.equalsIgnoreCase(fieldName));
	}

	/**
	 * @param store
	 */
	public void assembleStore(Store store) {
		// Validate that the positions entered are valid
		validatePositions(store);

		// Setting other factors
		assembleFactorValue(store, OtherFactorsField.SCHEDULE_INEFFICENCY);
		assembleFactorValue(store, OtherFactorsField.FILL_INEFFICIENCY);
		assembleFactorValue(store, OtherFactorsField.TRAINING_FACTOR);
		assembleFactorValue(store, OtherFactorsField.EARNED_BREAKS_FACTOR);
		assembleFactorValue(store, OtherFactorsField.FLOOR_MANAGEMENT_FACTOR);
		assembleFactorValue(store,
				OtherFactorsField.MINIMUM_FLOOR_MANAGEMENT_HOURS);

		// setting utilization non-guest service
		if (getNonGuestServiceUtilization() != null) {
			Double allPositionsUtilization = makePercentage(getNonGuestServiceUtilization());
			store.setAllPositionsUtilization(allPositionsUtilization);
		}

		// Setting Utilization & utilization limits
		Double value = null;
		Integer intValue = null;
		for (Position position : store.getPositions()) {

			// Utilization Bottom
			value = getUtilizationBottom().get(position.getName());
			if (value != null) {
				value = makePercentage(value);
				position.setUtilizationBottom(value);
			}

			// Utilization Top
			value = getUtilizationTop().get(position.getName());

			if (value != null) {
				value = makePercentage(value);
				position.setUtilizationTop(value);
			}

			// Utilization Limits Max
			intValue = getUtilizationLimitsMax().get(position.getName());
			if (value != null) {
				position.setUtilizationMaximum(intValue);
			}

			// Utilization Limits Min
			intValue = getUtilizationLimitsMin().get(position.getName());
			if (value != null) {
				position.setUtilizationMinimum(intValue);
			}

			// Minimum Staffing
			for (DayPartData dayPartData : position.getDayPartData()) {
				Integer staff = (Integer) minimumStaffing.get(
						position.getName(), dayPartData.getDayPart().getName());
				if (staff != null) {
					dayPartData.setMinimunStaffing(staff);
				}
			}

			// Activity Sharing
			String group = getActivitySharing().get(position.getName());
			if (group != null) {
				PositionGroup positionGroup = store
						.getPositionGroupByName(group);

				if (positionGroup != null)
					positionGroup.addPosition(position);
			}

		}
	}

	/**
	 * @param store
	 */
	private void validatePositions(Store store) {
		Set<String> storePositions = new HashSet<String>(store.getPositions()
				.size());

		for (Position position : store.getPositions()) {
			storePositions.add(position.getName());
		}

		Set<String> storePositionGroups = new HashSet<String>(store
				.getPositionGroups().size());

		for (PositionGroup positionGroup : store.getPositionGroups()) {
			storePositionGroups.add(positionGroup.getName());
		}

		Set<String> storeDayParts = new HashSet<String>(store.getDayParts()
				.size());

		for (DayPart dayPart : store.getDayParts()) {
			storeDayParts.add(dayPart.getName());
		}

		validatePositionError(storePositions, getUtilizationBottom().keySet(),
				LaborAssumptionField.UTILIZATION.getFieldName());
		validatePositionError(storePositions, getUtilizationTop().keySet(),
				LaborAssumptionField.UTILIZATION.getFieldName());
		validatePositionError(storePositions, getUtilizationLimitsMax()
				.keySet(),
				LaborAssumptionField.UTILIZATION_LIMITS.getFieldName());
		validatePositionError(storePositions, getUtilizationLimitsMin()
				.keySet(),
				LaborAssumptionField.UTILIZATION_LIMITS.getFieldName());
		validatePositionError(storePositions, getActivitySharing().keySet(),
				LaborAssumptionField.ACTIVITY_SHARING.getFieldName());
		validateSetParameterError(storePositionGroups, getActivitySharing()
				.values(), "Position Group",
				LaborAssumptionField.ACTIVITY_SHARING.getFieldName());
		validatePositionAndDayPartParameter(storePositions, storeDayParts,
				this.minimumStaffing,
				LaborAssumptionField.MINIMUM_STAFFING.getFieldName());
	}

	/**
	 * @param store
	 * @param otherFactor
	 */
	private void assembleFactorValue(Store store, OtherFactorsField otherFactor) {
		Double value = otherFactors.get(otherFactor.getFactorName());

		if (value == null)
			return;

		value = makePercentage(value);

		switch (otherFactor) {
		case EARNED_BREAKS_FACTOR:
			store.setEarnedBreakFactor(value);
			break;
		case FILL_INEFFICIENCY:
			store.setFillInefficiency(value);
			break;
		case FLOOR_MANAGEMENT_FACTOR:
			store.setFloorManagementFactor(value);
			break;
		case MINIMUM_FLOOR_MANAGEMENT_HOURS:
			store.setMinimumFloorManagementHours(value.intValue());
			break;
		case SCHEDULE_INEFFICENCY:
			store.setScheduleInefficiency(value);
			break;
		case TRAINING_FACTOR:
			store.setTrainingFactor(value);
			break;
		default:
			throw new IllegalArgumentException(
					"The factor passed in as parameter is not vald");
		}
	}

	/**
	 * @return the otherFactors
	 */
	public Map<String, Double> getOtherFactors() {
		return otherFactors;
	}

	/**
	 * @param otherFactors
	 *            the otherFactors to set
	 */
	public void setOtherFactors(Map<String, Double> otherFactors) {
		this.otherFactors = otherFactors;
	}

	/**
	 * @return the utilizationBottom
	 */
	public Map<String, Double> getUtilizationBottom() {
		return utilizationBottom;
	}

	/**
	 * @param utilizationBottom
	 *            the utilizationBottom to set
	 */
	public void setUtilizationBottom(Map<String, Double> utilizationBottom) {
		this.utilizationBottom = utilizationBottom;
	}

	/**
	 * @return the utilizationTop
	 */
	public Map<String, Double> getUtilizationTop() {
		return utilizationTop;
	}

	/**
	 * @param utilizationTop
	 *            the utilizationTop to set
	 */
	public void setUtilizationTop(Map<String, Double> utilizationTop) {
		this.utilizationTop = utilizationTop;
	}

	/**
	 * @return the nonGuestServiceUtilization
	 */
	public Double getNonGuestServiceUtilization() {
		return nonGuestServiceUtilization;
	}

	/**
	 * @param nonGuestServiceUtilization
	 *            the nonGuestServiceUtilization to set
	 */
	public void setNonGuestServiceUtilization(Double nonGuestServiceUtilization) {
		this.nonGuestServiceUtilization = nonGuestServiceUtilization;
	}

	/**
	 * @return the utilizationLimitsMin
	 */
	public Map<String, Integer> getUtilizationLimitsMin() {
		return utilizationLimitsMin;
	}

	/**
	 * @param utilizationLimitsMin
	 *            the utilizationLimitsMin to set
	 */
	public void setUtilizationLimitsMin(
			Map<String, Integer> utilizationLimitsMin) {
		this.utilizationLimitsMin = utilizationLimitsMin;
	}

	/**
	 * @return the utilizationLimitsMax
	 */
	public Map<String, Integer> getUtilizationLimitsMax() {
		return utilizationLimitsMax;
	}

	/**
	 * @param utilizationLimitsMax
	 *            the utilizationLimitsMax to set
	 */
	public void setUtilizationLimitsMax(
			Map<String, Integer> utilizationLimitsMax) {
		this.utilizationLimitsMax = utilizationLimitsMax;
	}

	/**
	 * @return the activitySharing
	 */
	public Map<String, String> getActivitySharing() {
		return activitySharing;
	}

	/**
	 * @param activitySharing
	 *            the activitySharing to set
	 */
	public void setActivitySharing(Map<String, String> activitySharing) {
		this.activitySharing = activitySharing;
	}

}
