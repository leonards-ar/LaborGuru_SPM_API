package com.laborguru.model.report;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.Area;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.1
 * @since SPM 1.1
 *
 */
public class TotalRegionManagerHour extends TotalManagerHour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859066865015430608L;
	
	private Area area;

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}

    @Override
    public String toString() {
       
        return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
        .append("area" , area)
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
        TotalRegionManagerHour obj = (TotalRegionManagerHour) other;
        if (area == null) {
            if (obj.area!= null)
                return false;
        } else if (!area.equals(obj.area)) return false;
        
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((area == null) ? 0 : area.hashCode());
        result += super.hashCode();
        return result;
    }

    public String getName() {
        return area != null ? area.getName() : null;
    }    
	
}
