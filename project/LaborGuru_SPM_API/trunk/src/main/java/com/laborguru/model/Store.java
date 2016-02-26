package com.laborguru.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.comparator.SpmComparator;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

public class Store extends SpmObject {
	
	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;	
	private String name;
	private String code;
	private DayOfWeek firstDayOfWeek;
	private Area area;
	private Integer dailyProjectionsWeeksDefault;
	private Integer halfHourProjectionsWeeksDefault;
	// We should stop using this attribute. Now this same value is stored
	// in the average property of the first variableDefinitions => getVariableDefinitions().get(0).getAverage()
	// At some point this variable will be removed. Right now when possible, every update will
	// set it to null
	@Deprecated
	private Double averageVariable;
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	private List<Position> positions;
	private List<OperationTime> operationTimes;
	private List<DayPart> dayParts;
	private Double allPositionsUtilization;
	private Set<PositionGroup> positionGroups;
	
	private Double scheduleInefficiency = null;
	private Double fillInefficiency = null;
	private Double trainingFactor = null;
	private Double earnedBreakFactor = null;
	private Double floorManagementFactor = null;
	private Integer minimumFloorManagementHours = null;
	
	private List<StoreVariableDefinition> variableDefinitions;
	private DistributionType distributionType;
	
	private boolean demo;
	// Default value for printing schedule. Managers can override this value before executing the report.
	private boolean inTimeOnly;
	
	/**
	 * Store toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("name",name)
	   	.append("code",code)
	   	.toString();		
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public DayPart getDayPartByName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		for(DayPart dp : getDayParts()) {
			if(name.equals(dp.getName())) {
				return dp;
			}
		}
		return null;
	}

	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Position getPositionByName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		for(Position position : getPositions()) {
			if(name.equals(position.getName())) {
				return position;
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public PositionGroup getPositionGroupByName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		for(PositionGroup pg : getPositionGroups()) {
			if(name.equals(pg.getName())) {
				return pg;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public PositionGroup getFirstPositionGroup() {
		return getPositionGroups().size() > 0 ? getPositionGroups().iterator().next() : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the day this store uses as the first day of
	 * the week. The possible values are:
	 * <ul>
	 * 	<li>0: Sunday</li>
	 * 	<li>1: Monday</li>
	 * 	<li>2: Tuesday</li>
	 * 	<li>3: Wednesday</li>
	 * 	<li>4: Thursday</li>
	 * 	<li>5: Friday</li>
	 * 	<li>6: Saturday</li>
	 * </ul>
	 * @return the firstDayOfWeek
	 */
	public Integer getFirstDayOfWeekAsInteger() {
		return getFirstDayOfWeek() != null ? new Integer(getFirstDayOfWeek().ordinal()) : null;
	}

	/**
	 * @param firstDayOfWeek the firstDayOfWeek to set
	 */
	public void setFirstDayOfWeekAsInteger(Integer firstDayOfWeek) {
		if(firstDayOfWeek != null) {
			setFirstDayOfWeek(DayOfWeek.values()[firstDayOfWeek.intValue()]);
		} else {
			setFirstDayOfWeek(null);
		}
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * @return the positions
	 */
	public List<Position> getPositions() {
		if(positions == null) {
			setPositions(new ArrayList<Position>());
		}
		return positions;
	}


	/**
	 * it is private to enforce the cardinality with the addPositions.
	 * DO NOT MAKE IT PUBLIC
	 * @param childMenuItems
	 */
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	

	/**
	 * Adds a position to the store. Handles the bi-directional
	 * relation.
	 * @param position The position to add
	 */
	public void addPosition(Position position){
		
		if (position == null){
			throw new IllegalArgumentException("Null position passed in as parameter");
		}
		
		if (position.getStore() != null){
			position.getStore().getPositions().remove(position);
		}
		
		position.setStore(this);
		getPositions().add(position);
	}	
	
	/**
	 * 
	 * @param variableDefinition
	 */
	public void addVariableDefinition(StoreVariableDefinition variableDefinition) {
		
		if (variableDefinition == null){
			throw new IllegalArgumentException("Null variableDefinition passed in as parameter");
		}
		
		if (variableDefinition.getStore() != null){
			variableDefinition.getStore().getVariableDefinitions().remove(variableDefinition);
		}
		
		variableDefinition.setStore(this);
		getVariableDefinitions().add(variableDefinition);		
	}
	 
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.name)
		.append(this.area != null? this.area.getId():null)
		.toHashCode();
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		
		
		final Store other = (Store) obj;
		
		return new EqualsBuilder()
		.append(this.area != null? this.area.getId():null, other.area != null? other.area.getId():null)
		.append(this.name, other.name)
		.isEquals();
	}

	
	/**
	 * Returns the operation time by date
	 * @return the operationTime
	 */
	public OperationTime getStoreOperationTimeByDate(Date date) {
	
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		
		for(OperationTime aOperationTime: getOperationTimes()) {
			if(dateCalendar.get(Calendar.DAY_OF_WEEK) == aOperationTime.getDayOfWeek().getDayOfWeek()) {
				return aOperationTime;
			}
		}
		
		return null;		
	}	
	
	/**
	 * @return the operationTime
	 */
	public List<OperationTime> getOperationTimes() {
		if(operationTimes == null) {
			setOperationTimes(new ArrayList<OperationTime>());
		}		
		return operationTimes;
	}

	/**
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public OperationTime getOperationTime(DayOfWeek dayOfWeek) {
		if(dayOfWeek == null) {
			throw new IllegalArgumentException("DayOfWeek cannot be null");
		}
		for(OperationTime ot : getOperationTimes()) {
			if(dayOfWeek.equals(ot.getDayOfWeek())) {
				return ot;
			}
		}
		return null;
	}
	
	/**
	 * @param operationTime the operationTime to set
	 */
	private void setOperationTimes(List<OperationTime> operationTimes) {
		this.operationTimes = operationTimes;
	}

	/**
	 * Adds an operation time (open and close hours) to the store.
	 * Handles the bi-directional relation.
	 * @param operationTime The operationTime to add
	 */
	public void addOperationTime(OperationTime operationTime){
		
		if (operationTime == null){
			throw new IllegalArgumentException("Null operationTime passed in as parameter");
		}
		
		if (operationTime.getStore() != null){
			operationTime.getStore().getOperationTimes().remove(operationTime);
		}
		
		operationTime.setStore(this);

		getOperationTimes().add(operationTime);
	}

	/**
	 * @return the firstDayOfWeek
	 */
	public DayOfWeek getFirstDayOfWeek() {
		if(firstDayOfWeek == null) {
			firstDayOfWeek = DayOfWeek.SUNDAY;
		}
		return firstDayOfWeek;
	}

	/**
	 * @param firstDayOfWeek the firstDayOfWeek to set
	 */
	public void setFirstDayOfWeek(DayOfWeek firstDayOfWeek) {
		this.firstDayOfWeek = firstDayOfWeek;
	}

	/**
	 * @return the dayParts
	 */
	public List<DayPart> getDayParts() {
		if(dayParts == null) {
			setDayParts(new ArrayList<DayPart>());
		}
		return dayParts;
	}

	/**
	 * @param dayParts the dayParts to set
	 */
	private void setDayParts(List<DayPart> dayParts) {
		this.dayParts = dayParts;
	}
	
	/**
	 * Adds a day part to the store. Handles the bi-directional
	 * relation.
	 * @param dayPart The day part to add
	 */
	public void addDayPart(DayPart dayPart){
		
		if (dayPart == null){
			throw new IllegalArgumentException("Null day part passed in as parameter");
		}
		
		if (dayPart.getStore() != null){
			dayPart.getStore().getDayParts().remove(dayPart);
		}
		
		dayPart.setStore(this);
		getDayParts().add(dayPart);
	}
	
	/**
	 * Removes a day part from the store. Handles the bi-directional
	 * relation.
	 * @param dayPart The day part to add
	 */
	public void removeDayPart(DayPart dayPart){
		
		if (dayPart == null){
			throw new IllegalArgumentException("Null day part passed in as parameter");
		}
				
		getDayParts().remove(dayPart);
		
		if (dayPart.getStore() != null){
			dayPart.setStore(null);
		}
	}

	/**
	 * @return the allPositionsUtilization
	 */
	public Double getAllPositionsUtilization() {
		return allPositionsUtilization;
	}

	/**
	 * @param allPositionsUtilization the allPositionsUtilization to set
	 */
	public void setAllPositionsUtilization(Double allPositionsUtilization) {
		this.allPositionsUtilization = allPositionsUtilization;
	}

	/**
	 * @return the postionGroups
	 */
	public Set<PositionGroup> getPositionGroups() {
		if(positionGroups == null) {
			setPositionGroups(new HashSet<PositionGroup>());
		}
		return positionGroups;
	}

	/** 
	 * Get Ordered PositionGroups
	 * @return
	 */
	public List<PositionGroup> getOrderedPositionGroups() {
		List<PositionGroup> orderedPositionGroups = new ArrayList<PositionGroup>(getPositionGroups());
		Collections.sort(orderedPositionGroups, new SpmComparator());
		return orderedPositionGroups;
	}
	
	/**
	 * @param postionGroups the postionGroups to set
	 */
	private void setPositionGroups(Set<PositionGroup> positionGroups) {
		this.positionGroups = positionGroups;
	}
	
	/**
	 * Add a positionGroup
	 * @param positionGroup
	 */
	public void addPositionGroup(PositionGroup positionGroup) {
		if (positionGroup == null){
			throw new IllegalArgumentException("Null position group passed in as parameter");
		}
		
		if (positionGroup.getStore() != null){
			positionGroup.getStore().getPositionGroups().remove(positionGroup);
		}
		
		positionGroup.setStore(this);
		getPositionGroups().add(positionGroup);
	}

	/**
	 * @return the dailyProjectionsWeeksDefault
	 */
	public Integer getDailyProjectionsWeeksDefault() {
		return dailyProjectionsWeeksDefault;
	}

	/**
	 * @param dailyProjectionsWeeksDefault the dailyProjectionsWeeksDefault to set
	 */
	public void setDailyProjectionsWeeksDefault(Integer dailyProjectionsWeeksDefault) {
		this.dailyProjectionsWeeksDefault = dailyProjectionsWeeksDefault;
	}

	/**
	 * @return the halfHourProjectionsWeeksDefault
	 */
	public Integer getHalfHourProjectionsWeeksDefault() {
		return this.halfHourProjectionsWeeksDefault;
	}

	/**
	 * @param halfHourProjectionsWeeksDefault the halfHourProjectionsWeeksDefault to set
	 */
	public void setHalfHourProjectionsWeeksDefault(
			Integer halfHourProjectionsWeeksDefault) {
		this.halfHourProjectionsWeeksDefault = halfHourProjectionsWeeksDefault;
	}

	/**
	 * @return the scheduleInefficiency
	 */
	public Double getScheduleInefficiency() {
		return scheduleInefficiency;
	}

	/**
	 * @param scheduleInefficiency the scheduleInefficiency to set
	 */
	public void setScheduleInefficiency(Double scheduleInefficiency) {
		this.scheduleInefficiency = scheduleInefficiency;
	}

	/**
	 * @return the fillInefficiency
	 */
	public Double getFillInefficiency() {
		return fillInefficiency;
	}

	/**
	 * @param fillInefficiency the fillInefficiency to set
	 */
	public void setFillInefficiency(Double fillInefficiency) {
		this.fillInefficiency = fillInefficiency;
	}

	/**
	 * @return the trainingFactor
	 */
	public Double getTrainingFactor() {
		return trainingFactor;
	}

	/**
	 * @param trainingFactor the trainingFactor to set
	 */
	public void setTrainingFactor(Double trainingFactor) {
		this.trainingFactor = trainingFactor;
	}

	/**
	 * @return the earnedBreakFactor
	 */
	public Double getEarnedBreakFactor() {
		return earnedBreakFactor;
	}

	/**
	 * @param earnedBreakFactor the earnedBreakFactor to set
	 */
	public void setEarnedBreakFactor(Double earnedBreakFactor) {
		this.earnedBreakFactor = earnedBreakFactor;
	}

	/**
	 * @return the floorManagementFactor
	 */
	public Double getFloorManagementFactor() {
		return floorManagementFactor;
	}

	/**
	 * @param floorManagementFactor the floorManagementFactor to set
	 */
	public void setFloorManagementFactor(Double floorManagementFactor) {
		this.floorManagementFactor = floorManagementFactor;
	}

	/**
	 * @return the minimumFloorManagementHours
	 */
	public Integer getMinimumFloorManagementHours() {
		return minimumFloorManagementHours;
	}

	/**
	 * @param minimumFloorManagementHours the minimumFloorManagementHours to set
	 */
	public void setMinimumFloorManagementHours(Integer minimumFloorManagementHours) {
		this.minimumFloorManagementHours = minimumFloorManagementHours;
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public DayPart getDayPartFor(Date time) {
		if(time != null) {
			DayPart dayPart, nextDayPart;
			if(getDayParts().size() > 1) {
				for(int i = 0; i < getDayParts().size(); i++) {
					dayPart = getDayParts().get(i);
					nextDayPart = i + 1 < getDayParts().size() ? getDayParts().get(i + 1) : getDayParts().get(0);
					if(dayPart != null && nextDayPart != null) {
						if(CalendarUtils.inRangeNotIncludingEndTime(time, dayPart.getStartHour(), nextDayPart.getStartHour())) {
							return dayPart;
						}
					}
				}
			} else {
				// For sure time is before the first day part
				dayPart = getDayParts().size() > 0 ? getDayParts().get(0) : null;
				return dayPart != null && dayPart.getStartHour() != null ? dayPart : null;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public Date getStoreScheduleStartHour(DayOfWeek dayOfWeek) {
		OperationTime operationTime = getOperationTime(dayOfWeek);
		Date startHour  = operationTime != null ? operationTime.getStartHour() : null;

		if(operationTime != null && startHour != null) {		
			OperationTime previousOperationTime = getOperationTime(dayOfWeek.getPreviousDayOfWeek());
			
			if(!CalendarUtils.greaterTime(previousOperationTime.getEndHour(), operationTime.getOpenHour())) {
				if(operationTime.startsYesterday() && !previousOperationTime.endsTomorrow() && CalendarUtils.greaterTime(previousOperationTime.getEndHour(), startHour)) {
					startHour = previousOperationTime.getEndHour();
				} else if(!operationTime.startsYesterday() && previousOperationTime.endsTomorrow() && CalendarUtils.greaterTime(previousOperationTime.getEndHour(), startHour)) {
					startHour = previousOperationTime.getEndHour();
				} else if(operationTime.startsYesterday() && previousOperationTime.endsTomorrow() && CalendarUtils.smallerTime(previousOperationTime.getEndHour(), startHour)) {
					startHour = previousOperationTime.getEndHour();
				}				
			} else {
				if(!operationTime.startsYesterday() && previousOperationTime.endsTomorrow() && CalendarUtils.greaterTime(previousOperationTime.getEndHour(), startHour)) {
					startHour = operationTime.getOpenHour();
				} else if(operationTime.startsYesterday() && previousOperationTime.endsTomorrow() && CalendarUtils.smallerTime(previousOperationTime.getEndHour(), startHour)) {
					startHour = operationTime.getOpenHour();
				}
			}
		}
		
		return startHour;
	}
	
	/**
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public Date getStoreScheduleEndHour(DayOfWeek dayOfWeek) {
		OperationTime operationTime = getOperationTime(dayOfWeek);
		Date endHour  = operationTime != null ? operationTime.getEndHour() : null;

		if(operationTime != null && endHour != null) {
			OperationTime nextOperationTime = getOperationTime(dayOfWeek.getNextDayOfWeek());

			// SPM#223: Should we be validating in this method? It should be enough to validate when saving the Operation Hours!
			// This lines make no sense based on the clases that use this method!
			/*
			if(operationTime.operationTimeEndsTomorrow() && CalendarUtils.greaterTime(operationTime.getCloseHour(), nextOperationTime.getOpenHour())) {
				throw new SpmUncheckedException("Close hour for [" + dayOfWeek + "] is after open hour for [" + dayOfWeek.getNextDayOfWeek() + "]", ErrorEnum.INVALID_OPERATION_TIME_FOR_STORE);
			}
			*/
			// The only way we modify end hour is if closing hours overlap with open time
			if(operationTime.endsTomorrow() && CalendarUtils.greaterTime(endHour, nextOperationTime.getOpenHour())) {
				endHour = nextOperationTime.getOpenHour();
			}
		}
		
		return endHour;
	}
	
	/**
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public boolean isMultiDaySchedule(DayOfWeek dayOfWeek) {
		OperationTime operationTime = getOperationTime(dayOfWeek);
		return CalendarUtils.equalsOrGreaterTime(operationTime.getOpenHour(), operationTime.getCloseHour());
	}	

	/**
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public boolean isMultiDayScheduleWithExtraHours(DayOfWeek dayOfWeek) {
		return CalendarUtils.equalsOrGreaterTime(getStoreScheduleStartHour(dayOfWeek), getStoreScheduleEndHour(dayOfWeek));
	}


	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}


	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}


	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


	/**
	 * @return the variableDefinitions
	 */
	public List<StoreVariableDefinition> getVariableDefinitions() {
		if(variableDefinitions == null) {
			setVariableDefinitions(new ArrayList<StoreVariableDefinition>());
		}
		// Main variable always exists!
		if(variableDefinitions.size() <= 0) {
			StoreVariableDefinition varDef = new StoreVariableDefinition();
			varDef.setVariableIndex(new Integer(0));
			varDef.setStore(this);
			varDef.setAverage(SpmConstants.DOUBLE_ONE_VALUE);
			variableDefinitions.add(varDef);
		}
		return variableDefinitions;		
	}


	/**
	 * @param variableDefinitions the variableDefinitions to set
	 */
	public void setVariableDefinitions(List<StoreVariableDefinition> variableDefinitions) {
		this.variableDefinitions = variableDefinitions;
	}
	
	/**
	 * 
	 * @return
	 */
	public StoreVariableDefinition getMainVariableDefinition() {
		return getVariableDefinitions().size() > 0 ? getVariableDefinitions().get(0) : null;
	}	

	/**
	 * 
	 * @param index
	 * @return
	 */
	public StoreVariableDefinition getSecondaryVariableDefinition(int index) {
		return getVariableDefinitions().size() > index ? getVariableDefinitions().get(index) : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isVariableDefinitionConfigured() {
		return getVariableDefinitions().size() > 1;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean isVariableDefinitionConfigured(int index) {
		return getSecondaryVariableDefinition(index) != null;
	}
	
	/**
	 * @return the distributionType
	 */
	public DistributionType getDistributionType() {
		return distributionType;
	}


	/**
	 * @param distributionType the projectionType to set
	 */
	public void setDistributionType(DistributionType projectionType) {
		this.distributionType = projectionType;
	}
	
	/**
	 * @return the distributionType
	 */
	public String getDistributionTypeAsString() {
		if (getDistributionType() != null)
		{
			return getDistributionType().name();
		}else{
			return DistributionType.HISTORIC_AVG.name();
		}		
	}
	
	/**
	 * @param distributionType the projectionType to set
	 */
	public void setDistributionType(String projectionType) {
		this.distributionType = Enum.valueOf(DistributionType.class, projectionType);
	}


	/**
	 * @return the averageVariable
	 * @deprecated Use getMainVariableAverage()
	 */
	@Deprecated
	public Double getAverageVariable() {
		return averageVariable;
	}


	/**
	 * @param averageVariable the averageVariable to set
	 * @deprecated Use setMainVariableAverage()
	 */
	@Deprecated
	public void setAverageVariable(Double averageVariable) {
		this.averageVariable = averageVariable;
	}

	public Double getMainVariableAverage() {
		return getMainVariableDefinition().getAverage();
	}
	
	public void setMainVariableAverage(Double averageVariable) {
		getMainVariableDefinition().setAverage(averageVariable);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDemo() {
		return demo;
	}

	/**
	 * 
	 * @param demo
	 */
	public void setDemo(boolean demo) {
		this.demo = demo;
	}


	/**
	 * @return the inTimeOnly
	 */
	public boolean isInTimeOnly() {
		return inTimeOnly;
	}


	/**
	 * @param inTimeOnly the inTimeOnly to set
	 */
	public void setInTimeOnly(boolean inTimeOnly) {
		this.inTimeOnly = inTimeOnly;
	}	
}
