package com.laborguru.model.report;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.Position;
import com.laborguru.model.SpmObject;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class TotalHourByPosition extends SpmObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 303432028624469673L;
	
	
	Position position;
	
	TotalHour totalHour;
	
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	/**
	 * @return the totalHour
	 */
	public TotalHour getTotalHour() {
		return totalHour;
	}
	/**
	 * @param totalHour the totalHour to set
	 */
	public void setTotalHour(TotalHour totalHour) {
		this.totalHour = totalHour;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
		.append(position)
		.append(totalHour)
		.toHashCode();

	}
	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		
		final TotalHourByPosition other = (TotalHourByPosition)obj;
		
		return new EqualsBuilder().append(position, other.position)
		.append(totalHour, other.totalHour).isEquals();
		
	}
	
	/**
	 * @return
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
		.append(position)
		.append(totalHour)
		.toString();
	}
	
}
