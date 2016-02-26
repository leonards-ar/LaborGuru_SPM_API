package com.laborguru.webservice.sales.binding;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.laborguru.model.HistoricSales;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class SalesItem {

	private HistoricSales historicSale;
	
	public SalesItem() {
		historicSale = new HistoricSales();
	}
	
	public String getHalfHour() {
		Date time = CalendarUtils.removeDateFromTime(historicSale.getDateTime());
		return CalendarUtils.dateToDisplayTime(time);
	}

	public void setHalfHour(String time) {
		historicSale.setDateTime(CalendarUtils.displayTimeToDate(time));
	}

	/**
	 * @return the mainValue
	 */
	public Double getMainValue() {
		return historicSale.getMainValue().doubleValue();
	}

	/**
	 * @param mainValue the mainValue to set
	 */
	public void setMainValue(Double mainValue) {
		historicSale.setMainValue(getBigDecimalValue(mainValue));
	}

	/**
	 * @return the secondValue
	 */
	public Double getSecondValue() {
		return historicSale.getSecondValue().doubleValue();
	}

	/**
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(Double secondValue) {
		if(secondValue != null) historicSale.setSecondValue(getBigDecimalValue(secondValue));
	}

	/**
	 * @return the thirdValue
	 */
	public Double getThirdValue() {
		return historicSale.getThirdValue().doubleValue();
	}

	/**
	 * @param thirdValue the thirdValue to set
	 */
	public void setThirdValue(Double thirdValue) {
		if(thirdValue != null) historicSale.setThirdValue(getBigDecimalValue(thirdValue));
	}

	/**
	 * @return the fourthValue
	 */
	public Double getFourthValue() {
		return historicSale.getFourthValue().doubleValue();
	}

	/**
	 * @param fourthValue the fourthValue to set
	 */
	public void setFourthValue(Double fourthValue) {
		if(fourthValue != null) historicSale.setFourthValue(getBigDecimalValue(fourthValue));
	}

	public HistoricSales getHistoricSale(){
		return historicSale;
	}

	private BigDecimal getBigDecimalValue(Double value) {
		return new BigDecimal(value).setScale(2, SpmConstants.ROUNDING_MODE);
	}
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("halfHour", getHalfHour())
		.append("mainValue", getMainValue())
		.toString();
	}
}
