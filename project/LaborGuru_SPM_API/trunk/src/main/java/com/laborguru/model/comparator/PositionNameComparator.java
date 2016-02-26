/**
 * 
 */
package com.laborguru.model.comparator;

import java.util.Comparator;

import com.laborguru.model.Position;

/**
 * @author mariano
 *
 */
public class PositionNameComparator implements Comparator<Position> {

	/**
	 * 
	 */
	public PositionNameComparator() {

	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Position o1, Position o2) {
		if(o1 != null && o1.getName() != null && o2 != null && o2.getName() != null) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		} else if((o1 != null && o1.getName() != null) && (o2 == null || o2.getName() == null)) {
			return 1;
		} else if((o2 != null && o2.getName() != null) && (o1 == null || o1.getName() == null)) {
			return -1;
		} else {
			return 0;
		}
	}

}
