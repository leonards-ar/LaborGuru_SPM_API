package com.laborguru.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
*
* @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
* @version 1.0
* @since SPM 1.1
*
*/
public class DailyFlashDetail extends SpmObject {


	private static final long serialVersionUID = 307046238358423508L;
	private Long id;
	private Date hour;
	private Double actualSale;
	private Double actualHour;
	private Double idealHour;
	private DailyFlash dailyFlash;
	
	public DailyFlashDetail(){
		
	}
	
	public DailyFlashDetail(String hour, String actualSale, String actualHour, String idealHour){
		
		this.hour = CalendarUtils.displayTimeToDate(hour);
		this.actualSale = Double.parseDouble(actualSale);
		this.actualHour = Double.parseDouble(actualHour);
		this.idealHour = Double.parseDouble(idealHour);
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHour() {
		return hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}
	
	public void setStrHour(String hour) {
		this.hour = CalendarUtils.inputTimeToDate(hour);
	}

	public Double getActualSale() {
		return actualSale;
	}

	public void setActualSale(Double actualSale) {
		this.actualSale = actualSale;
	}
	
	public void setStrActualSale(String actualSale) {
		this.actualSale = Double.parseDouble("-".equals(actualSale)? "0.0" : actualSale);
	}

	public Double getActualHour() {
		return actualHour;
	}

	public void setActualHour(Double actualHour) {
		this.actualHour = actualHour;
	}
	
	public void setStrActualHour(String actualHour) {
		this.actualHour = Double.parseDouble("-".equals(actualHour) ? "0.0" : actualHour);
	}
	
	public Double getIdealHour() {
		return idealHour;
	}

	public void setIdealHour(Double idealHour) {
		this.idealHour = idealHour;
	}
	
	public void setStrIdealHour(String idealHour) {
		this.idealHour = Double.parseDouble("-".equals(idealHour) ? "0.0" : idealHour);
	}

	public DailyFlash getDailyFlash() {
		return dailyFlash;
	}

	public void setDailyFlash(DailyFlash dailyFlash) {
		this.dailyFlash = dailyFlash;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , getId())
	   	.append("hour",getHour())
	   	.append("actualSale",getActualSale())
	   	.append("actualHour",getActualHour())
	   	.append("idealHour",getIdealHour())
	   	.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		

		DailyFlashDetail other = (DailyFlashDetail)obj;
		
		return new EqualsBuilder()
		.append(getHour(), other.getHour())
		.append((getDailyFlash()!= null)? getDailyFlash().getId():null, (other.getDailyFlash() != null)? other.getDailyFlash().getId():null)
		.isEquals();	
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getHour())
		.append(getDailyFlash())
		.toHashCode();		
	}
	
	public boolean isEmpty(){
		return SpmConstants.DOUBLE_ZERO_VALUE.equals(actualSale) && SpmConstants.DOUBLE_ZERO_VALUE.equals(actualHour) && SpmConstants.DOUBLE_ZERO_VALUE.equals(idealHour);
	}

}
