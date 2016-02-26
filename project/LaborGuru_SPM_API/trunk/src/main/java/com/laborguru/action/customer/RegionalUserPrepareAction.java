package com.laborguru.action.customer;

import java.util.List;

import com.laborguru.model.Manager;
import com.laborguru.model.Region;
import com.laborguru.model.RegionalUser;
import com.laborguru.service.region.RegionService;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class RegionalUserPrepareAction extends ManagerBaseAction implements
		Preparable {

	private String actionName = "regionalUser";
	private String previousActionName = "customer";
	private Region region;
	
	private RegionService regionService;
	
	@Override
	protected Manager getUserById() {
		RegionalUser tmpUser = new RegionalUser();
		tmpUser.setId(getUserId());
		return getManagerService().getManagerById((Manager)tmpUser);
	}

	@Override
	protected List<Manager> getUserList() {
		if(getRegion() == null) {
			loadRegion();
		}
		return (List<Manager>)getManagerService().getRegionalUsersByRegion(getRegion());
	}

	@Override
	protected void setExtraInformation() {
		if(getParamId() != null) {
			loadRegion();
		} else {
			setRegion(((RegionalUser)getManager()).getRegion());
		}
		setParamId(getRegion().getId());		
	}

	@Override
	protected void setSaveObject() {
		RegionalUser regionalUser = new RegionalUser(getUser());
		regionalUser.addProfile(getReferenceDataService().getRegionalRole());
		Region auxRegion = new Region();
		auxRegion.setId(getParamId());
		regionalUser.setRegion(auxRegion);
		setManager(regionalUser);
	}

	protected void setCriteria() {
		Region region = new Region();
		region.setId(getParamId());
		getSearchManager().setRegion(region);
	}
	
	public void prepare() throws Exception {
	}

	private void loadRegion(){
		Region auxRegion = new Region();
		auxRegion.setId(getParamId());
		setRegion(getRegionService().getRegionById(auxRegion));
	}
	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the previousActionName
	 */
	public String getPreviousActionName() {
		return previousActionName;
	}

	/**
	 * @param previousActionName the previousActionName to set
	 */
	public void setPreviousActionName(String previousActionName) {
		this.previousActionName = previousActionName;
	}

	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @return the regionService
	 */
	public RegionService getRegionService() {
		return regionService;
	}

	/**
	 * @param regionService the regionService to set
	 */
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

}
