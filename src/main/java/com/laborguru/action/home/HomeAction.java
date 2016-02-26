/*
 * File name: HomeAction.java
 * Creation date: 22/11/2008 12:48:27
 * Copyright Mindpool
 */
package com.laborguru.action.home;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.model.Profile;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class HomeAction extends SpmAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5686734450935309249L;

	/**
	 * 
	 */
	public HomeAction() {
	}
	


	/**
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		Profile loggedUserProfile = getLoggedUser().getProfile();
		String result = loggedUserProfile != null ? loggedUserProfile.getHomeResult() : null;
		
		resetMenu();

		if(result != null) {
			return result;
		} else {
			return SpmActionResult.SUCCESS.getResult();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
	}
}
