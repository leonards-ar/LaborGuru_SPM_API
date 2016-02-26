package com.laborguru.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.SpmConstants;

/**
 * Deals with sales data collected from the client.
 * 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HistoricSales extends SpmObject {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Date dateTime;
	private Integer dayOfWeek;
	private BigDecimal mainValue;
	
	private BigDecimal secondValue;
	private BigDecimal thirdValue;
	private BigDecimal fourthValue;
	
	private UploadFile uploadFile;
	private Store store;
	
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

		final HistoricSales other = (HistoricSales) obj;
		
		return new EqualsBuilder()
		.append(getDateTime(), other.getDateTime())
		.append(getMainValue(), other.getMainValue())
		.isEquals();	
	}

	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getDateTime())
		.append(getMainValue())
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
	   	.append("dateTime",getDateTime())
	   	.append("dow",getDayOfWeek())	   	
	   	.append("mainValue",getMainValue())
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
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * @param date the date to set
	 */
	public void setDateTime(Date date) {
		this.dateTime = date;
	}

	/**
	 * @return the dayOfWeek
	 */
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return the mainValue
	 */
	public BigDecimal getMainValue() {
		return mainValue != null ? mainValue : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param mainValue the mainValue to set
	 */
	public void setMainValue(BigDecimal mainValue) {
		this.mainValue = mainValue;
	}


	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * @return the uploadFile
	 */
	public UploadFile getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return the secondValue
	 */
	public BigDecimal getSecondValue() {
		return secondValue != null ? secondValue : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(BigDecimal secondValue) {
		this.secondValue = secondValue;
	}

	/**
	 * @return the thirdValue
	 */
	public BigDecimal getThirdValue() {
		return thirdValue != null ? thirdValue : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param thirdValue the thirdValue to set
	 */
	public void setThirdValue(BigDecimal thirdValue) {
		this.thirdValue = thirdValue;
	}

	/**
	 * @return the fourthValue
	 */
	public BigDecimal getFourthValue() {
		return fourthValue != null ? fourthValue : SpmConstants.BD_ZERO_VALUE;
	}

	/**
	 * @param fourthValue the fourthValue to set
	 */
	public void setFourthValue(BigDecimal fourthValue) {
		this.fourthValue = fourthValue;
	}

	public void setFieldForUpdate(HistoricSales hs){
		setFourthValue(hs.getFourthValue());
		setMainValue(hs.getMainValue());
		setSecondValue(hs.getSecondValue());
		setThirdValue(hs.getThirdValue());
		setUploadFile(hs.getUploadFile());
	}
	
	public BigDecimal getTotalValue() {
		return getMainValue().add(getSecondValue().add(getThirdValue().add(getFourthValue())));
	}
}
