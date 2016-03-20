package com.laborguru.model.report;

import java.math.BigDecimal;
import java.util.List;

import com.laborguru.util.SpmConstants;

public class DailyFlashObject {

	private BigDecimal variable2 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal variable3 = SpmConstants.BD_ZERO_VALUE;
	private BigDecimal variable4 = SpmConstants.BD_ZERO_VALUE;	
	
	private List<DailyFlashHour> dailyFlashHours;

	
	public BigDecimal getVariable2() {
		return variable2;
	}

	public void setVariable2(BigDecimal variable2) {
		this.variable2 = variable2;
	}

	public BigDecimal getVariable3() {
		return variable3;
	}

	public void setVariable3(BigDecimal variable3) {
		this.variable3 = variable3;
	}

	public BigDecimal getVariable4() {
		return variable4;
	}

	public void setVariable4(BigDecimal variable4) {
		this.variable4 = variable4;
	}

	public List<DailyFlashHour> getDailyFlashHours() {
		return dailyFlashHours;
	}

	public void setDailyFlashHours(List<DailyFlashHour> dailyFlashHour) {
		this.dailyFlashHours = dailyFlashHour;
	}
	
	
}
