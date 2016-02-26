package com.laborguru.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.laborguru.exception.ErrorEnum;
import com.laborguru.exception.ErrorMessage;
import com.laborguru.exception.SpmUncheckedException;
import com.laborguru.frontend.HttpRequestConstants;
import com.laborguru.frontend.model.SessionMenuWrapper;
import com.laborguru.model.Area;
import com.laborguru.model.AreaUser;
import com.laborguru.model.Customer;
import com.laborguru.model.CustomerUser;
import com.laborguru.model.Employee;
import com.laborguru.model.Region;
import com.laborguru.model.RegionalUser;
import com.laborguru.model.Store;
import com.laborguru.model.StoreVariableDefinition;
import com.laborguru.model.User;
import com.laborguru.util.CalendarUtils;
import com.laborguru.util.SpmConstants;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Spm Action Type
 * General point where we define common settings and task for SPM actions. 
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class SpmAction extends ActionSupport implements SessionAware,RequestAware{
	private static Logger log = Logger.getLogger(SpmAction.class);
	
	private Map session;
	private Map request;

	private List<String> variableNames = new ArrayList<String>(StoreVariableDefinition.MAX_VARIABLE_DEFINITIONS_QUANTITY);
	
	/**
	 * @return the projectionVariableNames
	 */
	public List<String> getVariableNames() {
		if (variableNames != null && variableNames.isEmpty()) {
			loadVariablesNames();
		}
		return variableNames;
	}

	/**
	 * @param variableNames the variableNames to set
	 */
	public void setVariableNames(List<String> variableNames) {
		this.variableNames = variableNames;
	}

	/**
	 * Sets the variable noames to display at projections page 
	 */
	protected void loadVariablesNames() {
		List<StoreVariableDefinition> variableDefinitions = getEmployeeStore().getVariableDefinitions();
		
		for (StoreVariableDefinition variableDef: variableDefinitions){
			variableNames.add(variableDef.getVariableIndex(), !StringUtils.isEmpty(variableDef.getName()) ? variableDef.getName() : getText("store.secondary.variable" + variableDef.getVariableIndex() + ".label"));
		}
		
		if (variableNames.size() < StoreVariableDefinition.MAX_VARIABLE_DEFINITIONS_QUANTITY){
			for(int i= variableNames.size(); i < StoreVariableDefinition.MAX_VARIABLE_DEFINITIONS_QUANTITY; i++){
				variableNames.add(i, null);
			}
		}
	}	
	
	/**
	 * Returns the logged user from session scope
	 * @param session The http request session
	 * @return The logged user
	 */
	protected User getLoggedUser() {
		return (User) getSession().get(HttpRequestConstants.USER);
	}
	
	/**
	 * Returns the logged employee only and only if it is an employee.
	 * If not, null is returned.
	 * @param session The http request session
	 * @return The logged employee
	 */
	protected Employee getLoggedEmployeeOrNull() {
		User user = getLoggedUser();
		return user instanceof Employee ? (Employee) user : null;
	}

	/**
	 * 
	 * @return
	 */
	public Map getSession() {
		return this.session;
	}
	
	/**
	 * 
	 * @param session
	 */
	public void setSession(Map session) {
		this.session = session;
	}
	
	/**
	 * Add error message to action errors from an ErrorMessage object
	 * @param errorMessage
	 */
	protected void addActionError(ErrorMessage errorMessage){
		addActionError(getText(errorMessage.getMessageKey(), errorMessage.getParameters()));
	}

	
   /**
    * @return the Client IP Address
    */
   public String getRemoteAddress() {
           return ServletActionContext.getRequest().getRemoteAddr();
   }

	/**
	 * Returns the store from the logged user if he is an employee or
	 * the store must be received as a parameter when an Administrator
	 * is creating store employees
	 * @return The store the employee belongs to
	 */
	protected Store getEmployeeStore() {
		Store store = getEmployeeStoreOrNull();
		if(store == null) {
			Employee employee = getLoggedEmployeeOrNull();
			if(employee != null) {
				store = employee.getStore();
				//Setting back the store to the session
				getSession().put(HttpRequestConstants.STORE,store);
			} else {
				throw new SpmUncheckedException("There is no store present in session. Called from the admin interface or session timed out?", ErrorEnum.NO_STORE_IN_SESSION);
			}
		}
		return store;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Store getEmployeeStoreOrNull() {
		return (Store) getSession().get(HttpRequestConstants.STORE);
	}
	
	protected Customer getCustomer() {
		Customer customer = (Customer) getSession().get(HttpRequestConstants.CUSTOMER);
		if(customer == null) {
			CustomerUser customerUser = (CustomerUser)getLoggedUser();
			customer = customerUser.getCustomer();
			if(customer != null) {
				getSession().put(HttpRequestConstants.CUSTOMER, customer);
			} else {
				throw new SpmUncheckedException("There is no customer present in session. Called from a user that is not a Customer Manager interface or session timed out?", ErrorEnum.NO_STORE_IN_SESSION);
			}
		}
		return customer;

	}
	
	protected Region getRegion() {
		Region region = (Region) getSession().get(HttpRequestConstants.REGION);
		if(region == null) {
			RegionalUser regionalUser = (RegionalUser)getLoggedUser();
			region = regionalUser.getRegion();
			if(region != null) {
				getSession().put(HttpRequestConstants.REGION, region);
			} else {
				throw new SpmUncheckedException("There is no region present in session. Called from a user that is not a Regional Manager interface or session timed out?", ErrorEnum.NO_STORE_IN_SESSION);
			}
		}
		return region;
	}
	
	protected Area getArea() {
		Area area = (Area) getSession().get(HttpRequestConstants.AREA);
		if(area == null) {
			AreaUser areaUser = (AreaUser)getLoggedUser();
			area = areaUser.getArea();
			if(area != null) {
				getSession().put(HttpRequestConstants.AREA, area);
			} else {
				throw new SpmUncheckedException("There is no area present in session. Called from a user that is not an Area Manager interface or session timed out?", ErrorEnum.NO_STORE_IN_SESSION);
			}
		}
		return area;
	}
	

	/**
	 * @return the request
	 */
	public Map getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(Map request) {
		this.request = request;
	}   
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	protected String dateToDisplayTime(Date d) {
		if(d != null) {
			return SpmConstants.TIME_FORMAT.format(d);
		} else {
			return null;
		}
	}	
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	protected Date displayTimeToDate(String time) {
		try {
			if(time != null) {
				return CalendarUtils.inputTimeToDate(time);
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error("Cannot parse time [" + time + "]", ex);
			return null;
		}
	}	
	
	/**
	 * 
	 * @return
	 */
	public String getMainVariableInitials() {
		if(getEmployeeStore() != null && getEmployeeStore().getMainVariableDefinition() != null) {
			return getEmployeeStore().getMainVariableDefinition().getVariableInitials();
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMainVariableName() {
		if(getEmployeeStore() != null && getEmployeeStore().getMainVariableDefinition() != null) {
			return getEmployeeStore().getMainVariableDefinition().getName();
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected SessionMenuWrapper getMenu() {
		SessionMenuWrapper menu = (SessionMenuWrapper) session.get(HttpRequestConstants.MENU);
		return menu;
	}
	
	/**
	 * 
	 */
	protected void resetMenu() {
		SessionMenuWrapper menu = getMenu();
		if(menu != null) {
			menu.reset();
		}
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public boolean isSecondaryVariablesConfigured(int index) {
		return getEmployeeStore().isVariableDefinitionConfigured(index);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSecondaryVariablesConfigured() {
		return getEmployeeStore().isVariableDefinitionConfigured();
	}	
}
