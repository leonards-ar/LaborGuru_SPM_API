/*
 * File name: MenuItemPositionComparator.java
 * Creation date: 14/06/2008 17:29:28
 * Copyright Mindpool
 */
package com.laborguru.model.comparator;

import java.util.Comparator;

import com.laborguru.model.MenuItem;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class MenuItemPositionComparator implements Comparator<MenuItem> {

	/**
	 * 
	 */
	public MenuItemPositionComparator() {
	}

	/**
	 * @param o1
	 * @param o2
	 * @return
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(MenuItem o1, MenuItem o2) {
		return o1.getPosition().compareTo(o2.getPosition());
	}

}
