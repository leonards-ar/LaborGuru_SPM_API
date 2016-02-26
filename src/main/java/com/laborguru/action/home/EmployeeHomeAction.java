/*
 * File name: EmployeeHomeAction.java
 * Creation date: 21/02/2009 15:22:11
 * Copyright Mindpool
 */
package com.laborguru.action.home;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class EmployeeHomeAction extends SpmAction implements Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1123623916987413899L;
	
	/**
	 * 
	 */
	public EmployeeHomeAction() {
	}

	/**
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {

	}

	/**
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SpmActionResult.SUCCESS.getResult();
	}
}
