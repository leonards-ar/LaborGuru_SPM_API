/*
 * File name: DailyStaffing.java
 * Creation date: 19/10/2008 15:46:47
 * Copyright Mindpool
 */
package com.laborguru.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public abstract class DailyStaffing extends SpmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7207256794677396300L;
	
	private Long id;
	private Date date;
	private Date startingTime;
	
	private Position position;
	
	private Double totalWorkContent = new Double(0.0);
	private Integer totalMinimumStaffing = new Integer(0);
	
	private Double totalVariableFlexible = new Double(0.0);
	private Double totalVariableOpening = new Double(0.0);
	private Double fixedFlexible = new Double(0.0);
	private Double fixedOpening = new Double(0.0);
	
	private Double totalServiceHours = new Double(0.0);

	private Double totalFlexible = new Double(0.0);
	private Double totalOpening = new Double(0.0);
	
	private Double fixedPostRush = new Double(0.0);
	private Double fixedClosing = new Double(0.0);
	
	private Double totalDailyTarget = new Double(0.0);
	
	/**
	 * 
	 */
	public DailyStaffing() {
	}

	/**
	 * @param obj
	 * @return
	 * @see com.laborguru.model.SpmObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		

		DailyStaffing other = (DailyStaffing) obj;
		
		return new EqualsBuilder()
		.append(getDate(), other.getDate())
		.append(getPosition(), other.getPosition())
		.isEquals();	
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getDate())
		.append(getPosition())
		.toHashCode();
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , getId())
	   	.append("date",getDate())
	   	.append("startingTime",getStartingTime())
	   	.toString();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = CalendarUtils.removeTimeFromDate(date);
	}

	/**
	 * @return the startingTime
	 */
	public Date getStartingTime() {
		return startingTime;
	}

	/**
	 * @param startingTime the startingTime to set
	 */
	public void setStartingTime(Date startingTime) {
		this.startingTime = startingTime;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getTotalHourStaffing() {
		return new Integer(getTotalMinimumStaffing().intValue() / 2);
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotalHourWorkContent() {
		return new Double(getTotalWorkContent().doubleValue() / 2);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Store getStore() {
		return getPosition() != null ? getPosition().getStore() : null;
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public int getHalfHourIndex(Date time) {
		if(time != null) {
			long t = time.getTime();
			int size = getHalfHourStaffing().size();
			for(int i = 0; i < size; i++) {
				HalfHourStaffing staffing = getHalfHourStaffing().get(i);
				if(t < staffing.getTime().getTime()) {
					return i > 0 ? i - 1 : 0;
				}
			}
			return size - 1;
		} else {
			return -1;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract List<? extends HalfHourStaffing> getHalfHourStaffing();
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public Integer getHalfHourStaffing(Date time) {
		if(time != null) {
			long t = time.getTime();
			for(HalfHourStaffing staffing : getHalfHourStaffing()) {
				if(t >= staffing.getTime().getTime()) {
					return staffing.getCalculatedStaff();
				}
			}
			return new Integer(0);
		} else {
			return new Integer(0);
		}		
	}

	/**
	 * 
	 * @param halfHourStaffing
	 */
	public abstract void addHalfHourStaffing(HalfHourStaffing halfHourStaffing);
	
	/**
	 * Removes halfHourCalculatedPositionStaff from the HalfHourCalculatedPositionStaffing list. Handles the bi-directional
	 * relation.
	 * @param halfHourStaffing The halfHourCalculatedPositionStaff to remove
	 */
	public void removeHalfHourStaffing(HalfHourStaffing halfHourStaffing){
		
		if (halfHourStaffing == null){
			throw new IllegalArgumentException("Null halfHourStaffing passed in as parameter");
		}
				
		getHalfHourStaffing().remove(halfHourStaffing);
		
		if (halfHourStaffing.getDailyStaffing() != null){
			halfHourStaffing.setDailyStaffing(null);
		}
		
		// :TODO: Update indexes
	}

	/**
	 * @return the totalWorkContent
	 */
	public Double getTotalWorkContent() {
		if(!NumberUtils.isValid(totalWorkContent)) {
			double total = 0.0;
			
			for(HalfHourStaffing aHalfHourStaffing : getHalfHourStaffing()) {
				total += aHalfHourStaffing.getWorkContent().doubleValue();
			}
			
			totalWorkContent = new Double(total);			
		}
		return totalWorkContent;
	}

	/**
	 * @param totalWorkContent the totalWorkContent to set
	 */
	public void setTotalWorkContent(Double totalWorkContent) {
		this.totalWorkContent = totalWorkContent;
	}

	/**
	 * 
	 * @return
	 */
	public Double getBaseDailyTarget() {
		return new Double(getTotalServiceHours().doubleValue() + getTotalFlexible().doubleValue() + getTotalOpening().doubleValue() + getFixedClosing().doubleValue());
	}

	/**
	 * @return the totalVariableFlexible
	 */
	public Double getTotalVariableFlexible() {
		if(!NumberUtils.isValid(totalVariableFlexible)) {
			setTotalVariableFlexible(new Double(0.0));
		}
		return totalVariableFlexible;
	}

	/**
	 * @param totalVariableFlexible the totalVariableFlexible to set
	 */
	public void setTotalVariableFlexible(Double totalVariableFlexible) {
		this.totalVariableFlexible = totalVariableFlexible;
	}

	/**
	 * @return the totalVariableOpening
	 */
	public Double getTotalVariableOpening() {
		if(!NumberUtils.isValid(totalVariableOpening)) {
			setTotalVariableOpening(new Double(0.0));
		}
		return totalVariableOpening;
	}

	/**
	 * @param totalVariableOpening the totalVariableOpening to set
	 */
	public void setTotalVariableOpening(Double totalVariableOpening) {
		this.totalVariableOpening = totalVariableOpening;
	}

	/**
	 * @return the totalServiceHours
	 */
	public Double getTotalServiceHours() {
		if(!NumberUtils.isValid(totalServiceHours)) {
			setTotalServiceHours(new Double(0.0));
		}
		return totalServiceHours;
	}

	/**
	 * @param totalServiceHours the totalServiceHours to set
	 */
	public void setTotalServiceHours(Double totalServiceHours) {
		this.totalServiceHours = totalServiceHours;
	}

	/**
	 * @return the totalFlexible
	 */
	public Double getTotalFlexible() {
		if(!NumberUtils.isValid(totalFlexible)) {
			setTotalFlexible(new Double(0.0));
		}
		return totalFlexible;
	}

	/**
	 * @param totalFlexible the totalFlexible to set
	 */
	public void setTotalFlexible(Double totalFlexible) {
		this.totalFlexible = totalFlexible;
	}

	/**
	 * @return the totalOpening
	 */
	public Double getTotalOpening() {
		if(!NumberUtils.isValid(totalOpening)) {
			setTotalOpening(new Double(0.0));
		}
		return totalOpening;
	}

	/**
	 * @param totalOpening the totalOpening to set
	 */
	public void setTotalOpening(Double totalOpening) {
		this.totalOpening = totalOpening;
	}

	/**
	 * @return the totalDailyTarget
	 */
	public Double getTotalDailyTarget() {
		if(!NumberUtils.isValid(totalDailyTarget)) {
			setTotalDailyTarget(new Double(0.0));
		}		
		return totalDailyTarget;
	}

	/**
	 * @param totalDailyTarget the totalDailyTarget to set
	 */
	public void setTotalDailyTarget(Double totalDailyTarget) {
		this.totalDailyTarget = totalDailyTarget;
	}

	/**
	 * @return the totalMinimumStaffing
	 */
	public Integer getTotalMinimumStaffing() {
		if(!NumberUtils.isValid(totalMinimumStaffing)) {
			int total = 0;
			
			for(HalfHourStaffing aHalfHourStaffing : getHalfHourStaffing()) {
				total += aHalfHourStaffing.getCalculatedStaff().intValue();
			}
			// Half hours to hours
			totalMinimumStaffing = new Integer(total);			
		}
		return totalMinimumStaffing;
	}

	/**
	 * @param totalMinimumStaffing the totalMinimumStaffing to set
	 */
	public void setTotalMinimumStaffing(Integer totalMinimumStaffing) {
		this.totalMinimumStaffing = totalMinimumStaffing;
	}

	/**
	 * @return the fixedFlexible
	 */
	public Double getFixedFlexible() {
		return fixedFlexible;
	}

	/**
	 * @param fixedFlexible the fixedFlexible to set
	 */
	public void setFixedFlexible(Double fixedFlexible) {
		this.fixedFlexible = fixedFlexible;
	}

	/**
	 * @return the fixedOpening
	 */
	public Double getFixedOpening() {
		if(!NumberUtils.isValid(fixedOpening)) {
			setFixedOpening(new Double(0.0));
		}		
		return fixedOpening;
	}

	/**
	 * @param fixedOpening the fixedOpening to set
	 */
	public void setFixedOpening(Double fixedOpening) {
		this.fixedOpening = fixedOpening;
	}

	/**
	 * @return the fixedPostRush
	 */
	public Double getFixedPostRush() {
		if(!NumberUtils.isValid(fixedPostRush)) {
			setFixedPostRush(new Double(0.0));
		}
		return fixedPostRush;
	}

	/**
	 * @param fixedPostRush the fixedPostRush to set
	 */
	public void setFixedPostRush(Double fixedPostRush) {
		this.fixedPostRush = fixedPostRush;
	}

	/**
	 * @return the fixedClosing
	 */
	public Double getFixedClosing() {
		if(!NumberUtils.isValid(fixedClosing)) {
			setFixedClosing(new Double(0.0));
		}
		return fixedClosing;
	}

	/**
	 * @param fixedClosing the fixedClosing to set
	 */
	public void setFixedClosing(Double fixedClosing) {
		this.fixedClosing = fixedClosing;
	}
}
