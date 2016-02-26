package com.laborguru.webservice.sales.binding;

import java.util.ArrayList;
import java.util.List;

public class SalesItemList {

    private List<SalesItem> salesItemListList = new ArrayList<SalesItem>();

    /** 
     * Get the list of 'salesItem' element items.
     * 
     * @return list
     */
    public List<SalesItem> getSalesItemLists() {
        return salesItemListList;
    }

    /** 
     * Set the list of 'salesItem' element items.
     * 
     * @param list
     */
    public void setSalesItemLists(List<SalesItem> list) {
        salesItemListList = list;
    }
    
    public String toString() {
    	return salesItemListList.toString();
    }
}
