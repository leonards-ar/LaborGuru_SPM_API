package com.laborguru.action.store;

import org.apache.log4j.Logger;

import com.laborguru.action.SpmActionResult;

/**
 * Action that handles the presentation layer for Projection Settings for an Store
 *  
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.1
 *
 */
public class ProjectionSettingsPrepareAction extends StoreAdministrationBaseAction {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ProjectionSettingsPrepareAction.class);

	private String distributionType;
	
	/**
	 * This method prepares the edit page
	 * 
	 * @return
	 */
	public String edit() {
		setDistributionType(getStore().getDistributionTypeAsString());
		return SpmActionResult.EDIT.getResult();
	}

	/**
	 * This method prepares the show page
	 * 
	 * @return
	 */
	public String show() {
		setDistributionType(getStore().getDistributionTypeAsString());		
		return SpmActionResult.SHOW.getResult();
	}

	/**
	 * Saves a store on the DB
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {

		if(log.isDebugEnabled()) {
			log.debug("About to save store: " + getStore());
		}
		
		//Set new date into store
		if (getDistributionType() != null){
			getStore().setDistributionType(getDistributionType());			
		}
		
		this.saveStoreAndLoadItIntoSession(getStore());

		if(log.isInfoEnabled()) {
			log.info("Store dayparts successfully updated for store with id [" + getStoreId() + "]");
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}

	/**
	 * @return the distributionType
	 */
	public String getDistributionType() {
		return distributionType;
	}

	/**
	 * @param distributionType the distributionType to set
	 */
	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}		
}
