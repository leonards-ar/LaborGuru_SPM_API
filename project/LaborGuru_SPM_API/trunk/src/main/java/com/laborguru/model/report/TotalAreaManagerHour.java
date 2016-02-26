package com.laborguru.model.report;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.laborguru.model.Store;

public class TotalAreaManagerHour extends TotalManagerHour {
    private static final long serialVersionUID = 3118328144912999607L;
    private Store store;

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
	
    @Override
    public String toString() {
       
        return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
        .append("store" , store)
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
        TotalAreaManagerHour obj = (TotalAreaManagerHour) other;
        if (store == null) {
            if (obj.store!= null)
                return false;
        } else if (!store.equals(obj.store)) return false;
        
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((store == null) ? 0 : store.hashCode());
        result += super.hashCode();
        return result;
    }
    
    public String getName() {
        return store != null?store.getName():null;
    }

}
