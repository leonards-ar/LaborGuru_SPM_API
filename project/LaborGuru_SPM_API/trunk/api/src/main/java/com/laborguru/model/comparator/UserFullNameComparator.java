/*
 * File name: UserFullNameComparator.java
 * Creation date: Jun 22, 2009 4:43:46 PM
 * Copyright Mindpool
 */
package com.laborguru.model.comparator;

import java.util.Comparator;

import com.laborguru.model.User;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class UserFullNameComparator implements Comparator<User> {

	/**
	 * 
	 */
	public UserFullNameComparator() {
	}


	/**
	 * 
	 * @param u1
	 * @param u2
	 * @return
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(User u1, User u2) {
		if(u1 != null && u1.getFullName() != null && u2 != null && u2.getFullName() != null) {
			return u1.getFullName().compareToIgnoreCase(u2.getFullName());
		} else if((u1 != null && u1.getFullName() != null) && (u2 == null || u2.getFullName() == null)) {
			return 1;
		} else if((u2 != null && u2.getFullName() != null) && (u1 == null || u1.getFullName() == null)) {
			return -1;
		} else {
			return 0;
		}
	}

}
