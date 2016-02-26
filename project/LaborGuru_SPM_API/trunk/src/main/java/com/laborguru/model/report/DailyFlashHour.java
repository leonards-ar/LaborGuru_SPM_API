package com.laborguru.model.report;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.laborguru.model.DayPart;
import com.laborguru.model.SpmObject;

public class DailyFlashHour extends SpmObject {

	private static final long serialVersionUID = -1078811451960999576L;

	private Date day;
	private DayPart dayPart;
	private BigDecimal sales;
	private BigDecimal actualSale;
	private BigDecimal scheduleHour;
	private BigDecimal targetHour;
	private BigDecimal actualHour;
	private BigDecimal idealHour;

	private static final DecimalFormat df = new DecimalFormat("#,##0");
	
	
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public DayPart getDayPart() {
		return dayPart;
	}

	public void setDayPart(DayPart dayPart) {
		this.dayPart = dayPart;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}	

	public BigDecimal getActualSale() {
		return actualSale;
	}

	public void setActualSale(BigDecimal actualSale) {
		this.actualSale = actualSale;
	}

	public BigDecimal getScheduleHour() {
		return scheduleHour;
	}

	public void setScheduleHour(BigDecimal scheduleHour) {
		this.scheduleHour = scheduleHour;
	}

	public BigDecimal getTargetHour() {
		return targetHour;
	}

	public void setTargetHour(BigDecimal targetHour) {
		this.targetHour = targetHour;
	}

	public BigDecimal getActualHour() {
		return actualHour;
	}

	public void setActualHour(BigDecimal actualHour) {
		this.actualHour = actualHour;
	}

	public BigDecimal getIdealHour() {
		return idealHour;
	}

	public void setIdealHour(BigDecimal idealHour) {
		this.idealHour = idealHour;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}
