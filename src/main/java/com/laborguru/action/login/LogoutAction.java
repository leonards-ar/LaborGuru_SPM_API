package com.laborguru.action.login;

import org.apache.struts2.dispatcher.SessionMap;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;

/**
 *
 * @author <a href="fbarrera@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class LogoutAction extends SpmAction {

	/**
	 *  Execute the action that will logout the user 
	 */
	public String execute() {
		((SessionMap)super.getSession()).invalidate();
		return SpmActionResult.LOGOUT.getResult();
	}
}
