package com.laborguru.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.util.CalendarUtils;

/**
*
* @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
* @version 1.0
* @since SPM 1.1
*
*/
public class DailyFlash extends SpmObject {
	
	private static final long serialVersionUID = 4027866434142349232L;
	private Long id;
	private Store store;
	private Date date;
	private Double openHours;
	private Double closeHours;
	private Double delivered;
	private Double planned;
	private Set<DailyFlashDetail> details = new HashSet<DailyFlashDetail>();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getOpenHours() {
		return openHours;
	}
	public void setOpenHours(Double openHours) {
		this.openHours = openHours;
	}
	public Double getCloseHours() {
		return closeHours;
	}
	public void setCloseHours(Double closeHours) {
		this.closeHours = closeHours;
	}
	public Set<DailyFlashDetail> getDetails() {
		return details;
	}
	public void setDetails(Set<DailyFlashDetail> details) {
		this.details = details;
	}	
	
	public Double getDelivered() {
		return delivered;
	}
	public void setDelivered(Double delivered) {
		this.delivered = delivered;
	}
	
	public Double getPlanned() {
		return planned;
	}
	public void setPlanned(Double planned) {
		this.planned = planned;
	}
	/**
	 * Adds an detail to this daily report. Handles the bi-directional
	 * relation.
	 * @param dailyFlashDetail The detail to add
	 */
	public void addDailyFlashDetail(DailyFlashDetail dailyFlashDetail){
		
		if (dailyFlashDetail == null){
			throw new IllegalArgumentException("Null dailyFlashDetail passed in as parameter");
		}
		
		if (dailyFlashDetail.getDailyFlash() != null){
			dailyFlashDetail.getDailyFlash().getDetails().remove(dailyFlashDetail);
		}
		
		DailyFlashDetail dfd = findByHour(dailyFlashDetail.getHour());
		if(dfd == null) {
			dailyFlashDetail.setDailyFlash(this);
			getDetails().add(dailyFlashDetail);
		} else {
			dfd.setActualHour(dailyFlashDetail.getActualHour());
			dfd.setActualSale(dailyFlashDetail.getActualSale());
			dfd.setIdealHour(dailyFlashDetail.getIdealHour());
		}
	}	
	@Override
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , getId())
	   	.append("date",getDate())
	   	.append("store",getStore())
	   	.append("openHours",getOpenHours())
	   	.append("closeHours",getCloseHours())
	   	.append("delivered",getDelivered())
	   	.append("planned",getPlanned())
	   	.append("Flash Details",getDetails())
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

		DailyFlash other = (DailyFlash) obj;
		
		return new EqualsBuilder()
		.append(getDate(), other.getDate())
		.append(getStore(), other.getStore())
		.isEquals();	
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(getDate())
		.append(getStore())
		.toHashCode();
	}
	
	private DailyFlashDetail findByHour(Date hour) {
		
		for(DailyFlashDetail dfd: getDetails()) {
			
			if(CalendarUtils.equalsTime(dfd.getHour(), hour)) {
				return dfd;
			}
		}
		
		return null;
	}
	
	

}
