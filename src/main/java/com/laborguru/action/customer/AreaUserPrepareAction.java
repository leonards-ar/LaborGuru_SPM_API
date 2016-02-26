package com.laborguru.action.customer;

import java.util.List;

import com.laborguru.model.Area;
import com.laborguru.model.AreaUser;
import com.laborguru.model.Manager;
import com.laborguru.service.area.AreaService;
import com.opensymphony.xwork2.Preparable;


/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */

@SuppressWarnings("serial")
public class AreaUserPrepareAction extends ManagerBaseAction implements Preparable{
	
	private String actionName = "areaUser";
	private String previousActionName = "customerRegion";

	private Area area;
	private AreaService areaService;
	
	/**
	 * @return
	 * @see com.laborguru.action.customer.ManagerBaseAction#getUserById()
	 */
	@Override
	protected Manager getUserById() {
		AreaUser tmpUser = new AreaUser();
		tmpUser.setId(getUserId());
		return getManagerService().getManagerById((Manager)tmpUser);
	}

	/**
	 * @return
	 * @see com.laborguru.action.customer.ManagerBaseAction#getUserList()
	 */
	@Override
	protected List<Manager> getUserList() {
		if(getArea() == null) {
			loadArea();
		}
		return (List<Manager>)getManagerService().getAreaUsersByArea(getArea());
	}

	
	protected void setCriteria(){
		Area auxArea = new Area();
		auxArea.setId(getParamId());
		getSearchManager().setArea(auxArea);
	}
	
	/**
	 * 
	 * @see com.laborguru.action.customer.ManagerBaseAction#setExtraInformation()
	 */
	@Override
	protected void setExtraInformation() {
		if(getParamId() != null) {
			loadArea();
		} else {
			setArea(((AreaUser)getManager()).getArea());
		}
		setParamId(getArea().getId());
		
	}

	/**
	 * 
	 * @see com.laborguru.action.customer.ManagerBaseAction#setSaveObject()
	 */
	@Override
	protected void setSaveObject() {
		AreaUser areaUser = new AreaUser(getUser());
		areaUser.addProfile(getReferenceDataService().getAreaRole());
		Area auxArea = new Area();
		auxArea.setId(getParamId());
		areaUser.setArea(auxArea);
		setManager(areaUser);		
	}


	/**
	 * Loads an Area
	 */
	private void loadArea() {
		Area auxArea = new Area();
		auxArea.setId(getParamId());
		setArea(getAreaService().getAreaById(auxArea));		
	}
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getPreviousActionName() {
		return previousActionName;
	}

	public void setPreviousActionName(String previousActionName) {
		this.previousActionName = previousActionName;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		//It's needed by the Preparable interface, don't comment out or removed
	}	
}
