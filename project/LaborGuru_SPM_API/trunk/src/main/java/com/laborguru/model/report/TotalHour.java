package com.laborguru.model.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.SpmObject;
import com.laborguru.util.NumberUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class TotalHour extends SpmObject {
	
	private static final long serialVersionUID = -7940750254658565313L;
	
	private Date day;
	private BigDecimal sales = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal variable2 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal variable3 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal variable4 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal schedule = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal target = SpmConstants.BD_ZERO_VALUE;
	private Double storeAverageVariable = SpmConstants.DOUBLE_ZERO_VALUE;
	private Double storeAverageVariable2 = SpmConstants.DOUBLE_ZERO_VALUE;
	private Double storeAverageVariable3 = SpmConstants.DOUBLE_ZERO_VALUE;
	private Double storeAverageVariable4 = SpmConstants.DOUBLE_ZERO_VALUE;
	private Double storeAverageWage = SpmConstants.DOUBLE_ZERO_VALUE;
	private Double storeTotalWage = SpmConstants.DOUBLE_ZERO_VALUE;
	
	/**
	 * 
	 */
	public TotalHour() {
		super();
	}
	
	/**
	 * 
	 * @param storeAverageVariable
	 * @param storeAverageWage
	 */
	public TotalHour(Double storeAverageVariable, Double storeAverageWage, Double storeTotalWage) {
		super();
		setStoreAverageVariable(storeAverageVariable);
		setStoreAverageWage(storeAverageWage);
		setStoreTotalWage(storeTotalWage);
	}
	
	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}
	/**
	 * @param column the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	/**
	 * @return the schedule
	 */
	public BigDecimal getSchedule() {
		return schedule != null ? schedule : SpmConstants.BD_ZERO_VALUE;
	}
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(BigDecimal schedule) {
		this.schedule = schedule;
	}
	/**
	 * @return the target
	 */
	public BigDecimal getTarget() {
		return target != null ? target : SpmConstants.BD_ZERO_VALUE;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(BigDecimal target) {
		this.target = target;
	}
	
	/**
	 * @return the sales
	 */
	public BigDecimal getSales() {
		return sales != null ? sales : SpmConstants.BD_ZERO_VALUE;
	}
	
	/**
	 * @param sales the sales to set
	 */
	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}
	/**
	 * @return the difference
	 */
	public BigDecimal getDifference() {
		return getSchedule().subtract(getTarget());
	}
	
	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		if(target.compareTo(SpmConstants.BD_ZERO_VALUE) == 0) {
			return SpmConstants.BD_ZERO_VALUE;
		}
		return getDifference().divide(target, 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100));
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getVplhSchedule() {
		if(schedule == null || schedule.compareTo(SpmConstants.BD_ZERO_VALUE) == 0) {
			return SpmConstants.BD_ZERO_VALUE;
		}
		return getTotal().divide(schedule, 2, SpmConstants.ROUNDING_MODE);		
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getProjectedSales() {
		BigDecimal var1 = new BigDecimal(NumberUtils.getDoubleValue(getStoreAverageVariable())).multiply(getSales());
		BigDecimal var2 =  new BigDecimal(NumberUtils.getDoubleValue(getStoreAverageVariable2())).multiply(getVariable2());
		BigDecimal var3 = new BigDecimal(NumberUtils.getDoubleValue(getStoreAverageVariable3())).multiply(getVariable3());
		BigDecimal var4 = new BigDecimal(NumberUtils.getDoubleValue(getStoreAverageVariable4())).multiply(getVariable4());
		
		return var1.add(var2.add(var3.add(var4)));
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getScheduleLaborPercentage() {
		BigDecimal ps = getProjectedSales();
		if(ps != null && ps.compareTo(SpmConstants.BD_ZERO_VALUE) != 0) {
			return getSchedule().multiply(getStoreAverageWageAsBigDecimal()).divide(ps, 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100));
		} else {
			return SpmConstants.BD_ZERO_VALUE;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getTargetLaborPercentage() {
		BigDecimal ps = getProjectedSales();
		if(ps != null && ps.compareTo(SpmConstants.BD_ZERO_VALUE) != 0) {
			return getTarget().multiply(getStoreAverageWageAsBigDecimal()).divide(ps, 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100));
		} else {
			return SpmConstants.BD_ZERO_VALUE;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getVplhTarget() {
		if(target == null || target.compareTo(SpmConstants.BD_ZERO_VALUE) == 0) {
			return SpmConstants.BD_ZERO_VALUE;
		}
		return getTotal().divide(target, 2, SpmConstants.ROUNDING_MODE);		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		final TotalHour other = (TotalHour) obj;
		
		return new EqualsBuilder().append(this.schedule, other.schedule)
		.append(this.target, other.target)
		.append(this.sales, other.sales)
		.append(this.day, other.day)
		.isEquals();		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(this.sales)
		.append(this.schedule)
		.append(this.target)
		.append(this.day)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
		.append(new SimpleDateFormat("E MM/dd").format(this.day))
		.append(this.sales)
		.append(this.schedule)
		.append(this.target)
		.append(getDifference())
		.append(getPercentage())
		.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getStoreAverageVariable() {
		return storeAverageVariable != null ? storeAverageVariable : SpmConstants.DOUBLE_ZERO_VALUE;
	}
	
	/**
	 * 
	 * @param storeAverageVariable
	 */
	public void setStoreAverageVariable(Double storeAverageVariable) {
		this.storeAverageVariable = storeAverageVariable;
	}

	/**
	 * 
	 * @return
	 */
	public Double getStoreAverageWage() {
		return storeAverageWage != null ? storeAverageWage : SpmConstants.DOUBLE_ZERO_VALUE;
	}

	/**
	 * 
	 * @return
	 */
	private BigDecimal getStoreAverageWageAsBigDecimal() {
		return new BigDecimal(NumberUtils.getDoubleValue(getStoreAverageWage()));
	}
	
	/**
	 * 
	 * @param storeAverageWage
	 */
	public void setStoreAverageWage(Double storeAverageWage) {
		this.storeAverageWage = storeAverageWage;
	}

	/**
	 * 
	 * @return
	 */
	public Double getStoreTotalWage() {
		return storeTotalWage != null ? storeTotalWage : SpmConstants.DOUBLE_ZERO_VALUE;
	}

	/**
	 * 
	 * @param storeTotalWage
	 */
	public void setStoreTotalWage(Double storeTotalWage) {
		this.storeTotalWage = storeTotalWage;
	}

	/**
	 * @return the variable2
	 */
	public BigDecimal getVariable2() {
		return variable2 != null ? variable2 : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param variable2 the variable2 to set
	 */
	public void setVariable2(BigDecimal variable2) {
		this.variable2 = variable2;
	}

	/**
	 * @return the variable3
	 */
	public BigDecimal getVariable3() {
		return variable3 != null ? variable3 : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param variable3 the variable3 to set
	 */
	public void setVariable3(BigDecimal variable3) {
		this.variable3 = variable3;
	}

	/**
	 * @return the variable4
	 */
	public BigDecimal getVariable4() {
		return variable4 != null ? variable4 : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param variable4 the variable4 to set
	 */
	public void setVariable4(BigDecimal variable4) {
		this.variable4 = variable4;
	}
	
	public BigDecimal getTotal() {
		return getSales().add(getVariable2()).add(getVariable3()).add(getVariable4());
	}

	/**
	 * @return the storeAverageVariable2
	 */
	public Double getStoreAverageVariable2() {
		return storeAverageVariable2 != null ? storeAverageVariable2 : SpmConstants.DOUBLE_ZERO_VALUE;
	}

	/**
	 * @param storeAverageVariable2 the storeAverageVariable2 to set
	 */
	public void setStoreAverageVariable2(Double storeAverageVariable2) {
		this.storeAverageVariable2 = storeAverageVariable2;
	}

	/**
	 * @return the storeAverageVariable3
	 */
	public Double getStoreAverageVariable3() {
		return storeAverageVariable3 != null ? storeAverageVariable3 : SpmConstants.DOUBLE_ZERO_VALUE;
	}

	/**
	 * @param storeAverageVariable3 the storeAverageVariable3 to set
	 */
	public void setStoreAverageVariable3(Double storeAverageVariable3) {
		this.storeAverageVariable3 = storeAverageVariable3;
	}

	/**
	 * @return the storeAverageVariable4
	 */
	public Double getStoreAverageVariable4() {
		return storeAverageVariable4 != null ? storeAverageVariable4 : SpmConstants.DOUBLE_ZERO_VALUE;
	}

	/**
	 * @param storeAverageVariable4 the storeAverageVariable4 to set
	 */
	public void setStoreAverageVariable4(Double storeAverageVariable4) {
		this.storeAverageVariable4 = storeAverageVariable4;
	}
}
