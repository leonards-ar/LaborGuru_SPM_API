package com.laborguru.frontend.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.laborguru.util.CalendarUtils;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

public class ActualValueElement {

	private BigDecimal mainValue;
	private BigDecimal actualVariable2;
	private BigDecimal actualVariable3;
	private BigDecimal actualVariable4;
	
	private Double hours;
	private Date date;
	
	/**
	 * @return the mainValue
	 */
	public BigDecimal getMainValue() {
		if (this.mainValue == null){
			setMainValue(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		return this.mainValue;
	}
	/**
	 * @param mainValue the mainValue to set
	 */
	public void setMainValue(BigDecimal mainValue) {
		this.mainValue = mainValue;
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
		this.date = date;
	}
	
	public Boolean getEditable() {
		if(System.getProperty(SpmConstants.DEBUG) != null) {
			return Boolean.TRUE;
		} else {			
			return (CalendarUtils.todayWithoutTime().compareTo(getDate()) >= 0);
		}
	}	

	public int getMainValueToDisplay(){
		return NumberUtils.bigDecimalToInt(getMainValue());
	}
	
	public String toString(){
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
	   	.append("mainValue" , getMainValue())
	   	.append("hours" , getHours())
	   	.append("date",getDate())
	   	.toString();		
		
	}
	/**
	 * @return the actualHours
	 */
	public Double getHours() {
		if (this.hours == null)
		{
			setHours(0.0);
		}
		
		return hours;
	}
	/**
	 * @param actualHours the actualHours to set
	 */
	public void setHours(Double actualHours) {
		this.hours = actualHours;
	}
	/**
	 * @return the actualVariable2
	 */
	public BigDecimal getActualVariable2() {
		if (this.actualVariable2 == null){
			setActualVariable2(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		return actualVariable2;
	}
	/**
	 * @param actualVariable2 the actualVariable2 to set
	 */
	public void setActualVariable2(BigDecimal actualVariable2) {
		this.actualVariable2 = actualVariable2;
	}
	/**
	 * @return the actualVariable3
	 */
	public BigDecimal getActualVariable3() {
		if (this.actualVariable3 == null){
			setActualVariable3(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		
		return actualVariable3;
	}
	/**
	 * @param actualVariable3 the actualVariable3 to set
	 */
	public void setActualVariable3(BigDecimal actualVariable3) {
		this.actualVariable3 = actualVariable3;
	}
	/**
	 * @return the actualVariable4
	 */
	public BigDecimal getActualVariable4() {
		if (this.actualVariable4 == null){
			setActualVariable4(new BigDecimal(SpmConstants.INIT_VALUE_ZERO));
		}
		
		return actualVariable4;
	}
	/**
	 * @param actualVariable4 the actualVariable4 to set
	 */
	public void setActualVariable4(BigDecimal actualVariable4) {
		this.actualVariable4 = actualVariable4;
	}

	public int getActualVariable2ToDisplay(){
		return NumberUtils.bigDecimalToInt(getActualVariable2());
	}	
	
	public int getActualVariable3ToDisplay(){
		return NumberUtils.bigDecimalToInt(getActualVariable3());
	}	
	
	public int getActualVariable4ToDisplay(){
		return NumberUtils.bigDecimalToInt(getActualVariable4());
	}	
}
