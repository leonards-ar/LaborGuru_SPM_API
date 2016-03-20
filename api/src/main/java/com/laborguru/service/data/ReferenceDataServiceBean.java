/*
 * File name: ReferenceDataServiceBean.java
 * Creation date: 22/06/2008 20:36:13
 * Copyright Mindpool
 */
package com.laborguru.service.data;

import java.util.List;
import java.util.Map;

import com.laborguru.model.Profile;

/**
 *
 * @author <a href="mcapurro@gmail.com">Mariano Capurro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class ReferenceDataServiceBean implements ReferenceDataService {
	private Map<String, Object> referenceData;
	
	/**
	 * 
	 */
	public ReferenceDataServiceBean() {
	}

	/**
	 * @param country
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getStates(java.lang.String)
	 */
	public List<String> getStates(String country) {
		return (List<String>) getReferenceData().get("states-" + country.toLowerCase());
	}

	/**
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getStatus()
	 */
	public Map<String, String> getStatus() {
		return (Map<String, String>) getReferenceData().get("status");
	}

	/**
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getUsedWeeks()
	 */
	public Map<Integer, String> getUsedWeeks() {
		return (Map<Integer, String>) getReferenceData().get("usedWeeks");
	}	

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getManagerRoleId()
	 */
	public Profile getManagerRole() {
		return (Profile)getReferenceData().get("managerRole");
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getEmployeeRoleId()
	 */
	public Profile getEmployeeRole() {
		return (Profile)getReferenceData().get("employeeRole");
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getAdministratorRole()
	 */
	public Profile getAdministratorRole() {
		return (Profile)getReferenceData().get("adminRole");
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getCustomerRole()
	 */
	public Profile getCustomerRole() {
		return (Profile)getReferenceData().get("customerRole");
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getRegionalRole()
	 */
	public Profile getRegionalRole() {
		return (Profile)getReferenceData().get("regionalRole");
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getAreaRole()
	 */
	public Profile getAreaRole() {
		return (Profile)getReferenceData().get("areaRole");
	}	
	
	/**
	 * @return the referenceData
	 */
	public Map<String, Object> getReferenceData() {
		return referenceData;
	}

	/**
	 * @param referenceData the referenceData to set
	 */
	public void setReferenceData(Map<String, Object> referenceData) {
		this.referenceData = referenceData;
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getScheduleViews()
	 */
	public Map<String, String> getScheduleViews() {
		return (Map<String, String>) getReferenceData().get("scheduleViews");
	}

	/**
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getReportViews()
	 */
	public Map<String, String> getReportTypes() {
		return (Map<String, String>) getReferenceData().get("reportTypes");
	}
	
	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getReportGrouping()
	 */
	public Map<String, String> getReportGrouping() {
		return (Map<String, String>) getReferenceData().get("reportGrouping");
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.service.data.ReferenceDataService#getWeeklyScheduleViews()
	 */
	public Map<String, String> getWeeklyScheduleViews() {
		return (Map<String, String>) getReferenceData().get("weeklyScheduleViews");
	}

	public Map<String, String> getPrintScheduleViews() {
		return (Map<String, String>) getReferenceData().get("printScheduleViews");
	}
	
	public Map<String, String> getPrintDailyScheduleViews() {
		return (Map<String, String>) getReferenceData().get("printDailyScheduleViews");
	}
	
	public Map<String,String>getHistoricalReportDataType() {
		return (Map<String, String>) getReferenceData().get("historicalDataType");
	}
	
}
