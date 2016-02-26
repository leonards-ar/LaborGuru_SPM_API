package com.laborguru.action.security;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.AccessDeniedHandlerImpl;
import org.apache.log4j.Logger;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;

public class AccessDeniedAction extends SpmAction {

	private static final Logger log = Logger.getLogger(AccessDeniedAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1454366842217802662L;
	
	/**
	 * 
	 * @return
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		Object message = getRequest().get(AccessDeniedHandlerImpl.ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);
		log.error(message);
		if(SecurityContextHolder.getContext().getAuthentication() !=null ){
		  log.error(SecurityContextHolder.getContext().getAuthentication().toString());
		}
		return SpmActionResult.SUCCESS.getResult();
	}
	
}
