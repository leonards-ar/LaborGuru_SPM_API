/*
 * File name: AddShiftBaseAction.java
 * Creation date: Sep 20, 2008 1:17:19 PM
 * Copyright Mindpool
 */
package com.laborguru.action.schedule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.laborguru.model.DailyProjectedStaffing;
import com.laborguru.model.Employee;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.Shift;
import com.laborguru.model.comparator.UserFullNameComparator;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.employee.EmployeeService;
import com.laborguru.service.position.PositionService;
import com.laborguru.service.positionGroup.PositionGroupService;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.schedule.ScheduleService;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class AddShiftBaseAction extends ScheduleShiftBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AddShiftBaseAction.class);
	
	private Map<String, String> scheduleViewsMap = null;

	private String selectView;
	
	private ScheduleService scheduleService;
	private EmployeeService employeeService;
	private StaffingService staffingService;
	private ProjectionService projectionService;
	private ReferenceDataService referenceDataService;
	private PositionService positionService;
	private PositionGroupService positionGroupService;
	
	private String saveSchedule;
	private String copySchedule;

	private Date copyTargetDay;
	
	private List<Position> positions;
	private List<PositionGroup> positionGroups;
	private List<Employee> employees;
	
	private String positionSelectId;
	
	private Position position;
	private PositionGroup positionGroup;
	
	private List<Position> selectedPositions;
	
	private Double hiddenTotalScheduled;
	
	/**
	 * 
	 */
	public AddShiftBaseAction() {
	}

	/**
	 * 
	 * @return
	 */
	public String getBreakId() {
		return SpmConstants.SCHEDULE_BREAK;
	}
	
	/**
	 * 
	 */
	protected abstract void loadCopyTargetDay();
	
	/**
	 * 
	 * @return
	 */
	protected Position getPosition(Integer positionId) {
		if(positionId != null) {
			Position position = new Position();
			position.setId(positionId);
			position = getPositionService().getPositionById(position);
			return position;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param positionGroupId
	 * @return
	 */
	protected PositionGroup getPositionGroup(Integer positionGroupId) {
		if(positionGroupId != null) {
			PositionGroup positionGroup = new PositionGroup();
			positionGroup.setId(positionGroupId);
			positionGroup = getPositionGroupService().getPositionGroupById(positionGroup);
			positionGroup.getPositions();
			return positionGroup;
		} else {
			return null;
		}
	}
	
	/**
	 * @return the scheduleService
	 */
	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * @param scheduleService the scheduleService to set
	 */
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	/**
	 * @return the employeeService
	 */
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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

	/**
	 * @return the projectionService
	 */
	public ProjectionService getProjectionService() {
		return projectionService;
	}

	/**
	 * @param projectionService the projectionService to set
	 */
	public void setProjectionService(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}

	/**
	 * 
	 */
	protected abstract void loadScheduleViewsMap();

	/**
	 * @return the scheduleViewsMap
	 */
	public Map<String, String> getScheduleViewsMap() {
		if(scheduleViewsMap == null) {
			loadScheduleViewsMap();
		}
		return scheduleViewsMap;
	}

	/**
	 * @param scheduleViewsMap the scheduleViewsMap to set
	 */
	public void setScheduleViewsMap(Map<String, String> scheduleViewsMap) {
		this.scheduleViewsMap = scheduleViewsMap;
	}

	/**
	 * @return the referenceDataService
	 */
	public ReferenceDataService getReferenceDataService() {
		return referenceDataService;
	}

	/**
	 * @param referenceDataService the referenceDataService to set
	 */
	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	/**
	 * @return the selectView
	 */
	public String getSelectView() {
		return selectView;
	}

	/**
	 * @param selectView the selectView to set
	 */
	public void setSelectView(String selectView) {
		this.selectView = selectView;
	}
	
	/**
	 * 
	 */
	protected abstract void initializeSelectView();
	
	/**
	 * @return the saveSchedule
	 */
	public String getSaveSchedule() {
		return saveSchedule;
	}

	/**
	 * @param saveSchedule the saveSchedule to set
	 */
	public void setSaveSchedule(String saveSchedule) {
		this.saveSchedule = saveSchedule;
	}	
	

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	/**
	 * @return the copyTargetDay
	 */
	public Date getCopyTargetDay() {
		return copyTargetDay;
	}

	/**
	 * @param copyTargetDay the copyTargetDay to set
	 */
	public void setCopyTargetDay(Date copyTargetDay) {
		this.copyTargetDay = copyTargetDay;
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
	 * @param positions the positions to set
	 */
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	/**
	 * 
	 */
	protected void loadPositions() {
		this.setPositions(getPositionService().getPositionsByStore(getEmployeeStore()));
		this.setPositionGroups(getPositionGroupService().getPositionGroupsByStore(getEmployeeStore()));
	}
	
	/**
	 * 
	 */
	protected void loadEmployees() {
		setEmployees(getEmployeeService().getEmployeesByStore(getEmployeeStore()));
		Collections.sort(getEmployees(), new UserFullNameComparator());
	}
	
	/**
	 * @return the position
	 */
	public Position getPosition() {
		if(Position.isValidUniqueId(getPositionSelectId())) {
			if(position == null || position.getId() == null || position.getName() == null) {
				setPosition(getPosition(Position.getId(getPositionSelectId())));
			}
		} else {
			setPosition(null);
		}
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Compares two positions by their ID
	 * @param pos1
	 * @param pos2
	 */
	protected boolean isEqualPosition(Position pos1, Position pos2) {
		return pos1 != null && pos2 != null && isEqualId(pos1.getId(), pos2.getId());
	}

	/**
	 * 
	 * @param id1
	 * @param id2
	 * @return
	 */
	protected boolean isEqualId(Integer id1, Integer id2) {
		return id1 != null && id1.equals(id2);
	}
	

	
	/**
	 * 
	 * @param source
	 * @param destination
	 */
	protected void updateShift(Shift source, Shift destination) {
		destination.setFromHour(source.getFromHour());
		destination.setToHour(source.getToHour());
		destination.setPosition(source.getPosition());
		// Reset contiguous shift
		destination.setContiguousShift(null);
		destination.setStartingShift(null);
	}	

	/**
	 * 
	 * @param dailyStaffing
	 * @return
	 */
	protected int getTotalDailyStaffingInMinutes(DailyProjectedStaffing dailyStaffing) {
		Integer mins = CalendarUtils.hoursToMinutes(dailyStaffing != null ? dailyStaffing.getTotalDailyTarget() : new Double(0.0));
		return mins != null ? mins.intValue() : 0;
	}
	
	/**
	 * 
	 * @return
	 */
	protected int getTotalTargetInMinutes(Collection<DailyProjectedStaffing> storeDailyStaffing) {
		int total = 0;
		
		for(DailyProjectedStaffing dailyStaffing : storeDailyStaffing) {
			total += getTotalDailyStaffingInMinutes(dailyStaffing);
		}
		
		return total;
	}

	/**
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		if(employees == null) {
			setEmployees(new ArrayList<Employee>());
		}
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	/**
	 * @return the hiddenTotalScheduled
	 */
	protected Double getHiddenTotalScheduled() {
		return hiddenTotalScheduled;
	}

	/**
	 * @param hiddenTotalScheduled the hiddenTotalScheduled to set
	 */
	protected void setHiddenTotalScheduled(Double hiddenTotalScheduled) {
		this.hiddenTotalScheduled = hiddenTotalScheduled;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getHiddenTotalScheduledInMinutes() {
		return CalendarUtils.hoursToMinutes(getHiddenTotalScheduled());
	}
	
	/**
	 * 
	 * @param hiddenTotalScheduledInMinutes
	 */
	public void setHiddenTotalScheduledInMinutes(Integer hiddenTotalScheduledInMinutes) {
		
	}

	/**
	 * @return the selectedPositions
	 */
	public List<Position> getSelectedPositions() {
		if(selectedPositions == null || selectedPositions.size() <= 0) {
			selectedPositions = new ArrayList<Position>();
			if(getPosition() != null) {
				selectedPositions.add(getPosition());
			} else if(getPositionGroup() != null) {
				selectedPositions.addAll(getPositionGroup().getPositions());
			} else {
				selectedPositions.addAll(getPositions());
			}
		}
		return selectedPositions;
	}

	/**
	 * Returns true if all positions are selected
	 * @return
	 */
	protected boolean isAllPositions() {
		return getPosition() == null && getPositionGroup() == null;
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	protected List<Position> getPositionInList(Position position) {
		List<Position> positions = new ArrayList<Position>();
		positions.add(position);
		return positions;
	}
	
	protected boolean isByPositionView() {
		return false;
	}
	
	/**
	 * 
	 * @param position
	 */
	protected boolean isPositionInList(List<Position> positions, Position position) {
		if(position != null && positions != null) {
			if(!isAllPositions() || isByPositionView()) {
				for(Position pos : positions) {
					if(isEqualPosition(pos, position)) {
						return true;
					}
				}
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * @param selectedPositions the selectedPositions to set
	 */
	public void setSelectedPositions(List<Position> selectedPositions) {
		this.selectedPositions = selectedPositions;
	}

	/**
	 * 
	 * @return
	 */
	public List<PositionGroup> getPositionGroups() {
		if(positionGroups == null) {
			setPositionGroups(new ArrayList<PositionGroup>());
		}
		return positionGroups;
	}

	/**
	 * 
	 * @param positionGroups
	 */
	public void setPositionGroups(List<PositionGroup> positionGroups) {
		this.positionGroups = positionGroups;
	}

	/**
	 * 
	 * @return
	 */
	public PositionGroup getPositionGroup() {
		if(PositionGroup.isValidUniqueId(getPositionSelectId())) {
			if(positionGroup == null) {
				setPositionGroup(getPositionGroup(PositionGroup.getId(getPositionSelectId())));
			}
		} else {
			setPosition(null);
		}
		return positionGroup;
	}

	/**
	 * 
	 * @param positionGroup
	 */
	public void setPositionGroup(PositionGroup positionGroup) {
		this.positionGroup = positionGroup;
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract Double getVplhSchedule();

	/**
	 * 
	 * @return
	 */
	public abstract Double getVplhTarget();

	/**
	 * 
	 * @return
	 */
	public abstract Double getLaborPercentageSchedule();

	/**
	 * 
	 * @return
	 */
	public abstract Double getLaborPercentageTarget();

	/**
	 * 
	 * @return
	 */
	public abstract Double getAverageWage();
	
	/**
	 * 
	 * @return
	 */
	public double getStoreAverageVariable() {
		return NumberUtils.getDoubleValue(getEmployeeStore().getMainVariableAverage());
	}
	
	/**
	 * 
	 */
	protected abstract void initializeCopyTargetDay();

	/**
	 * 
	 * @return
	 */
	public String getPositionSelectId() {
		return positionSelectId;
	}

	/**
	 * 
	 * @param positionSelectId
	 */
	public void setPositionSelectId(String positionSelectId) {
		this.positionSelectId = positionSelectId;
	}

	/**
	 * 
	 * @return
	 */
	public PositionGroupService getPositionGroupService() {
		return positionGroupService;
	}

	/**
	 * 
	 * @param positionGroupService
	 */
	public void setPositionGroupService(PositionGroupService positionGroupService) {
		this.positionGroupService = positionGroupService;
	}

	public String getCopySchedule() {
		return copySchedule;
	}

	public void setCopySchedule(String copySchedule) {
		this.copySchedule = copySchedule;
	}
}
