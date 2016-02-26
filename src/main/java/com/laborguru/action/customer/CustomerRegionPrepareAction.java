package com.laborguru.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.frontend.HttpRequestConstants;
import com.laborguru.model.Area;
import com.laborguru.model.Region;
import com.laborguru.service.region.RegionService;
import com.opensymphony.xwork2.Preparable;

public class CustomerRegionPrepareAction extends SpmAction implements Preparable{

	private static final long serialVersionUID = 1L;

	private RegionService regionService;

	private Region region = new Region();
	
	private List<Area> areas = new ArrayList<Area>();
	
	private Integer regionId;
	
	private String regionName;
	
	private Integer customerId;

	private Integer index;
	
	private String newAreaName;
	
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

	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	
	/**
	 * Prepares the edit page
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		loadCustomerId();
		loadRegionFromId();
		setRegionName(getRegion().getName());
		
		setAreas(new ArrayList<Area>(getRegion().getAreas()));

		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String addArea() {
		getAreas().add(getNewArea(getNewAreaName()));
		setNewAreaName(null);
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 * @return
	 */
	public String removeArea() {
		if(getIndex() != null && getIndex().intValue() >= 0 && getIndex().intValue() < getAreas().size()) {
			getAreas().remove(getIndex().intValue());
		}
		
		return SpmActionResult.EDIT.getResult();
	}
	
	/**
	 * 
	 */
	private void loadCustomerId() {
		Integer id = getCustomerId();
		//:TODO: Better way to communicate actions in Struts 2?
		if(id == null) {
			id = (Integer) getSession().get(HttpRequestConstants.CUSTOMER_TO_EDIT_ID);
			setCustomerId(id);
		} else {
			getSession().put(HttpRequestConstants.CUSTOMER_TO_EDIT_ID, id);
		}		
	}
	
	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * 
	 */
	private void setAreaNames() {
		if(getNewAreaName() != null && getNewAreaName().trim().length() > 0) {
			getAreas().add(getNewArea(getNewAreaName()));
		}
		
		Set<Area> areasToRemove = new HashSet<Area>();
		
		// Update existing areas
		for (Area regionArea : getRegion().getAreas()) {
			Area anArea = getAreaById(getAreas(), regionArea.getId());
			if(anArea != null) {
				regionArea.setName(anArea.getName());
			} else {
				areasToRemove.add(regionArea);
			}
		}

		// Remove areas
		for(Area areaToRemove : areasToRemove) {
			getRegion().removeArea(areaToRemove);
		}
		
		// Add new areas
		for(Area anArea : getAreas()) {
			if(getAreaById(getRegion().getAreas(), anArea.getId()) == null) {
				getRegion().addArea(anArea);
			}
		}
	}

	/**
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	private Area getAreaById(Collection<Area> areas, Integer id) {
		if(id == null) {
			return null;
		}
		
		for(Area anArea : areas) {
			if(id.equals(anArea.getId())) {
				return anArea;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private Area getNewArea(String name) {
		Area newArea =  new Area();
		newArea.setName(name);
		newArea.setRegion(getRegion());
		return newArea;
	}
	
	/**
	 * 
	 * @return
	 */
	public String save() {
		loadRegionFromId();
		getRegion().setName(getRegionName());
		
		setAreaNames();
		
		try {
			getRegionService().save(getRegion());
		} catch(SpmCheckedException ex) {
			addActionError(ex.getErrorMessage());
		}
		
		return SpmActionResult.SUCCESS.getResult();
	}
	
	/**
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @return the areas
	 */
	public List<Area> getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	/**
	 *  Load full customer from the property customerId
	 */
	private void loadRegionFromId() {
		Region tmpRegion = new Region();
		tmpRegion.setId(getRegionId());
		this.setRegion(getRegionService().getRegionById(tmpRegion));
	}
	
	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		//It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the newAreaName
	 */
	public String getNewAreaName() {
		return newAreaName;
	}

	/**
	 * @param newAreaName the newAreaName to set
	 */
	public void setNewAreaName(String newAreaName) {
		this.newAreaName = newAreaName;
	}	
}
