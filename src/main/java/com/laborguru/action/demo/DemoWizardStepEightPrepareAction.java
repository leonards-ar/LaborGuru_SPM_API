/**
 * 
 */
package com.laborguru.action.demo;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Employee;
import com.laborguru.model.Store;
import com.laborguru.service.email.EmailService;

/**
 * @author mariano
 *
 */
public class DemoWizardStepEightPrepareAction extends DemoWizardBaseAction {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DemoWizardStepEightPrepareAction.class);
	
	private EmailService emailService;

	/**
	 * 
	 */
	public DemoWizardStepEightPrepareAction() {
	}

	/**
	 * @see com.laborguru.action.demo.DemoWizardBaseAction#validateSession()
	 */
	@Override
	public boolean validateSession() {
		return true;
	}

	/**
	 * @see com.laborguru.action.demo.DemoWizardBaseAction#executeStep()
	 */
	@Override
	public String executeStep() throws Exception {
		try {
			//:TODO: Save store & save employee should be a transaction!
			Store store = getDemoStore();
			getEmployee().setStore(store);
			getEmployeeService().save(getEmployee());
			
			setStoreName(store.getName());
			setEmployeeUserName(getEmployee().getUserName());

			notify(store, getEmployee());
			
			getSession().remove(STORE_SESSION_KEY);
			getSession().remove(EMPLOYEE_SESSION_KEY);
			
			return SpmActionResult.STEP_8.getResult();
		} catch(SpmCheckedException ex) {
			addActionError(ex.getErrorMessage());
			return SpmActionResult.INPUT.getResult();
		}
	}
	
	/**
	 * 
	 * @param store
	 * @param employee
	 */
	private void notify(Store store, Employee employee) {
		try {
			String to = getText("demo.wizard.notify.to");
			String subject = getText("demo.wizard.notify.subject", new String[] {store.getName(), employee.getUserName()});
			String body = getText("demo.wizard.notify.body", new String[] {store.getName(), employee.getUserName()});
			getEmailService().sendEmail(new String[]{to}, null, subject, body);
		} catch(Throwable ex) {
			log.error("Cannot send new demo store creation [" + store + "]", ex);
		}		
	}

	/**
	 * 
	 * @return
	 */
	public EmailService getEmailService() {
		return emailService;
	}

	/**
	 * 
	 * @param emailService
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
}
