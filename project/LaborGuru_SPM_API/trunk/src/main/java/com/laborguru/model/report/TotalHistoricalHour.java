package com.laborguru.model.report;

import java.math.BigDecimal;

import com.laborguru.model.SpmObject;
import com.laborguru.util.SpmConstants;

/**
*
* @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
* @version 1.0
* @since SPM 1.0
*
*/
public class TotalHistoricalHour extends SpmObject {

	private static final long serialVersionUID = 9172595222859581067L;
	TotalHour totalHour;
	BigDecimal actualTrend;
	BigDecimal idealTrend;
	
	public TotalHistoricalHour(TotalHour totalHour){
		
		this.totalHour = totalHour;
		
	}
	
	public TotalHour getTotalHour() {
		return totalHour;
	}


	public void setTotalHour(TotalHour totalHour) {
		this.totalHour = totalHour;
	}


	public BigDecimal getActualTrend() {
		return actualTrend;
	}
	
	public void setActualTrend(BigDecimal actualTrend) {
		this.actualTrend = actualTrend;
	}
	
	public BigDecimal getIdealTrend() {
		return idealTrend;
	}
	
	public void setIdealTrend(BigDecimal idealTrend) {
		this.idealTrend = idealTrend;
	}
	
	public BigDecimal getTrendPercentage(){
		if(idealTrend.compareTo(SpmConstants.BD_ZERO_VALUE) == 0) {
			return SpmConstants.BD_ZERO_VALUE;
		}
		return getActualTrend().divide(idealTrend, 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100));
	}
	
	public BigDecimal getPercentage(){
		if(totalHour.getTarget().compareTo(SpmConstants.BD_ZERO_VALUE) == 0) {
			return SpmConstants.BD_ZERO_VALUE;
		}
		return totalHour.getSchedule().divide(totalHour.getTarget(), 2, SpmConstants.ROUNDING_MODE).multiply(new BigDecimal(100));
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
