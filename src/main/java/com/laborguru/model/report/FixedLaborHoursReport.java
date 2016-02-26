package com.laborguru.model.report;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.SpmObject;

public class FixedLaborHoursReport extends SpmObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5877746783884339639L;
	FixedLaborHours schedule;
	FixedLaborHours target;

	public FixedLaborHoursReport() {
		schedule = initObject();
		target = initObject();
	}
	
	/**
	 * @return the schedule
	 */
	public FixedLaborHours getSchedule() {
		return schedule ;
	}
	
	/**
	 * @return the target
	 */
	public FixedLaborHours getTarget() {
		return target;
	}
	
	public FixedLaborHours getDifference() {
		
		FixedLaborHours fixedLaborHours = new FixedLaborHours();
		
		fixedLaborHours.setServiceHours(schedule.getServiceHours() - target.getServiceHours());
		fixedLaborHours.setOpenHours(schedule.getOpenHours() - target.getOpenHours());
		fixedLaborHours.setCloseHours(schedule.getCloseHours() - target.getCloseHours());
		fixedLaborHours.setFlexHours(schedule.getFlexHours() - target.getFlexHours());
		
		return fixedLaborHours;
	}
	
	public Double getTotalSchedule() {
		double total = 0.0;
		if(schedule.getServiceHours() != null) {
			total += schedule.getServiceHours();
		}
		
		if(schedule.getOpenHours() != null) {
			total += schedule.getOpenHours();
		}
		
		if(schedule.getCloseHours() != null) {
			total += schedule.getCloseHours();
		}
		
		if(schedule.getFlexHours() != null) {
			total += schedule.getFlexHours();
		}
		
		return new Double(total);
		
	}
	
	public Double getTotalTarget() {
		double total = 0.0;
		if(target.getServiceHours() != null) {
			total += target.getServiceHours();
		}
		
		if(target.getOpenHours() != null) {
			total += target.getOpenHours();
		}
		
		if(target.getCloseHours() != null) {
			total += target.getCloseHours();
		}
		
		if(target.getFlexHours() != null) {
			total += target.getFlexHours();
		}
		
		return new Double(total);
	}
	
	public Double getTotalDifference() {
		FixedLaborHours difference = this.getDifference();
		double total = 0.0;
		if(difference.getServiceHours() != null) {
			total += difference.getServiceHours();
		}
		
		if(difference.getOpenHours() != null) {
			total += difference.getOpenHours();
		}
		
		if(difference.getCloseHours() != null) {
			total += difference.getCloseHours();
		}
		
		if(difference.getFlexHours() != null) {
			total += difference.getFlexHours();
		}
		
		return new Double(total);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final FixedLaborHoursReport other = (FixedLaborHoursReport) obj;
		
		return new EqualsBuilder()
				.append(this.schedule, other.schedule)
				.append(this.target, other.target)
				.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37)
		.append(this.schedule)
		.append(this.target)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
		.append(this.schedule)
		.append(this.target)
		.toString();
	}
	
	private FixedLaborHours initObject() {
		FixedLaborHours fixedLaborHours = new FixedLaborHours();
		
		fixedLaborHours.setServiceHours(0.0);
		fixedLaborHours.setOpenHours(0.0);
		fixedLaborHours.setCloseHours(0.0);
		fixedLaborHours.setFlexHours(0.0);
		
		return fixedLaborHours;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(FixedLaborHours schedule) {
		this.schedule = schedule;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(FixedLaborHours target) {
		this.target = target;
	}

	
}
