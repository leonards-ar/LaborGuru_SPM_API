package com.laborguru.model.report;

import java.math.BigDecimal;
import java.util.Date;

import com.laborguru.model.HalfHourFlashSales;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 * Class that encapsulates {@link com.laborguru.model.HalfHourHistoricSales}
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HalfHourIdealSales {
	
	HalfHourFlashSales halfHourFlashSale;
	
	public HalfHourIdealSales() {
		halfHourFlashSale = new HalfHourFlashSales();
	}
	
	public Date getTime() {
		return halfHourFlashSale.getTime();
	}
	
	public void setTime(Date time) {
		halfHourFlashSale.setTime(time);
	}
	
	public BigDecimal getSale() {
		return halfHourFlashSale.getValue();
	}
	
	public void setSale(BigDecimal sale) {
		halfHourFlashSale.setValue(sale);
	}
	
	public void setStrTime(String time) {
		halfHourFlashSale.setTime(CalendarUtils.inputTimeToDate(time));
	}
	
	public void setStrSale(String sale) {
		halfHourFlashSale.setValue("-$".equals(sale)? SpmConstants.BD_ZERO_VALUE : new BigDecimal(sale.replace("$", "").replace(",","")));
	}
	
	public HalfHourFlashSales getHalfHourFlashSales() {
		return this.halfHourFlashSale;
	}
	

}
