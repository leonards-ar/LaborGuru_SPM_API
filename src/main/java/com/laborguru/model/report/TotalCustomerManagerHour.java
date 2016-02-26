package com.laborguru.model.report;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.Region;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class TotalCustomerManagerHour extends TotalManagerHour {

	private static final long serialVersionUID = -4263736677595272709L;
	
	private Region region;
	
	
	/**
	 * @return the customer
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

    @Override
    public String toString() {
       
        return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
        .append("region" , region)
        .append("sales" , sales)
        .append("schedule", schedule)
        .append("target", target)
        .toString();
    }

    @Override
    public boolean equals(Object other) {
       
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (getClass() != other.getClass())
            return false;
        TotalCustomerManagerHour obj = (TotalCustomerManagerHour) other;
        if (region == null) {
            if (obj.region!= null)
                return false;
        } else if (!region.equals(obj.region)) return false;
        
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((region == null) ? 0 : region.hashCode());
        result += super.hashCode();
        return result;
    }
    
    public String getName() {
        return region != null ? region.getName() : null;
    }
}
