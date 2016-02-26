package com.laborguru.action.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.DailyProjection;
import com.laborguru.model.Position;
import com.laborguru.model.PositionGroup;
import com.laborguru.model.StoreSchedule;
import com.laborguru.service.position.PositionService;
import com.laborguru.service.positionGroup.PositionGroupService;
import com.laborguru.service.projection.ProjectionService;
import com.laborguru.service.schedule.ScheduleService;
import com.laborguru.service.staffing.StaffingService;
import com.laborguru.util.NumberUtils;
import com.opensymphony.xwork2.Preparable;

public class PercentageLaborHoursReportPrepareAction extends ScheduleReportPrepareAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9088006513047913789L;

	private PositionService positionService;
	private PositionGroupService positionGroupService;
	private ScheduleService scheduleService;
	private ProjectionService projectionService;
	private StaffingService staffingService;
	
	private List<Position> selectedPositions;
	private StoreSchedule storeSchedule;
	private Double totalHoursSchedule;
	private Double totalHoursTarget;
	private BigDecimal volume;
	private DailyProjection dailyProjection;
	private Map<Integer, Double> staffingTotals;
	
	public void prepare() throws Exception {
	}

	public String showReport() {
		initWeekSelectorValues();
		
		return SpmActionResult.SHOW.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	private List<Position> getSelectedPositions() {
		if(selectedPositions == null) {
			if(getItemId() == null) {
				selectedPositions = getPositionService().getPositionsByStore(getEmployeeStore());
			} else if ("byPosition".equals(getSelectedGrouping())) {
				selectedPositions = new ArrayList<Position>();
				Position pos = new Position();
				pos.setId(getItemId());
				pos = getPositionService().getPositionById(pos);
				if(pos != null) {
					selectedPositions.add(pos);
				}
			} else if ("byService".equals(getSelectedGrouping())) {
				selectedPositions = new ArrayList<Position>();
				PositionGroup posGroup = new PositionGroup();
				posGroup.setId(getItemId());
				posGroup = getPositionGroupService().getPositionGroupById(posGroup);
				if(posGroup != null) {
					selectedPositions.addAll(posGroup.getPositions());
				}
			} else {
				selectedPositions = getPositionService().getPositionsByStore(getEmployeeStore());
			}		
		}
		return selectedPositions;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getVplhSchedule() {
		double totalHs = NumberUtils.getDoubleValue(getTotalHoursSchedule());
		if(totalHs > 0.0) {
			return new Double(NumberUtils.getDoubleValue(getVolume()) / totalHs);
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 
	 * @return
	 */
	private BigDecimal getVolume() {
		if(volume == null) {
			volume = getDailyProjection().getDailyProjectionValue();
		}
		return volume;
	}
	/**
	 * 
	 * @return
	 */
	private Double getTotalHoursSchedule() {
		//:TODO: filter by position??
		if(totalHoursSchedule == null) {
			totalHoursSchedule = getStoreSchedule().getTotalShiftHours();			
		}
		return totalHoursSchedule;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getVplhTarget() {
		double totalHs = NumberUtils.getDoubleValue(getTotalHoursTarget());
		if(totalHs > 0) {
			return new Double(NumberUtils.getDoubleValue(getVolume()) / totalHs);
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 
	 * @return
	 */
	private Double getTotalHoursTarget() {
		//:TODO: filter by position??
		if(totalHoursTarget == null) {
			double total = 0.0;
			for(Double d : getStaffingTotals().values()) {
				total += NumberUtils.getDoubleValue(d);
			}
			totalHoursTarget = new Double(total);				
		}
		return totalHoursTarget;
	}
	
	/**
	 * 
	 * @return
	 */
	protected double getStoreAverageVariable() {
		return NumberUtils.getDoubleValue(getEmployeeStore().getMainVariableAverage());
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageSchedule() {
		double d = NumberUtils.getDoubleValue(getVolume()) * getStoreAverageVariable();
		if(d != 0.0) {
			return (NumberUtils.getDoubleValue(getAverageWage()) * getTotalHoursSchedule() / d) * 100;
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 
	 * @return
	 */	
	private Double getAverageWage() {
		double totalWage = 0.0;
		double totalHours = 0.0;
		totalWage += getStoreSchedule().getTotalWage();
		totalHours += getStoreSchedule().getTotalShiftHours();
		return new Double(totalWage / totalHours);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getLaborPercentageTarget() {
		double d = NumberUtils.getDoubleValue(getVolume()) * getStoreAverageVariable();
		if(d != 0.0) {
			return (NumberUtils.getDoubleValue(getAverageWage()) * getTotalHoursTarget() / d) * 100;
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 
	 * @return
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * 
	 * @param positionService
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
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

	/**
	 * 
	 * @return
	 */
	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * 
	 * @param scheduleService
	 */
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	/**
	 * 
	 * @return
	 */
	public StoreSchedule getStoreSchedule() {
		if(storeSchedule == null) {
			setStoreSchedule(getScheduleService().getStoreScheduleByDate(getEmployeeStore(), getWeekDaySelector().getSelectedDay()));			
		}		
		return storeSchedule;
	}

	/**
	 * 
	 * @param storeSchedule
	 */
	public void setStoreSchedule(StoreSchedule storeSchedule) {
		this.storeSchedule = storeSchedule;
	}

	/**
	 * 
	 * @return
	 */
	public ProjectionService getProjectionService() {
		return projectionService;
	}

	/**
	 * 
	 * @param projectionService
	 */
	public void setProjectionService(ProjectionService projectionService) {
		this.projectionService = projectionService;
	}

	/**
	 * 
	 * @return
	 */
	public DailyProjection getDailyProjection() {
		if(dailyProjection == null) {
			setDailyProjection(getProjectionService().getDailyProjection(getEmployeeStore(), getWeekDaySelector().getSelectedDay()));
		}
		return dailyProjection;
	}

	/**
	 * 
	 * @param dailyProjection
	 */
	public void setDailyProjection(DailyProjection dailyProjection) {
		this.dailyProjection = dailyProjection;
	}

	/**
	 * 
	 * @return
	 */
	public StaffingService getStaffingService() {
		return staffingService;
	}

	/**
	 * 
	 * @param staffingService
	 */
	public void setStaffingService(StaffingService staffingService) {
		this.staffingService = staffingService;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, Double> getStaffingTotals() {
		if(staffingTotals == null) {
			setStaffingTotals(getStaffingService().getTotalProjectedStaffingByPositionForTimePeriod(getEmployeeStore(), getWeekDaySelector().getSelectedDay(), getWeekDaySelector().getSelectedDay()));
		}
		return staffingTotals;
	}

	/**
	 * 
	 * @param staffingTotals
	 */
	public void setStaffingTotals(Map<Integer, Double> staffingTotals) {
		this.staffingTotals = staffingTotals;
	}

}
