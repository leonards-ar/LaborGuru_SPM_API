package com.laborguru.action.customer;

import java.util.List;
import java.util.Map;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Manager;
import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.model.filter.SearchManagerFilter;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.manager.ManagerService;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public abstract class ManagerBaseAction extends SpmAction {

	private Integer paramId;
	private Integer userId;
	private Manager manager;
	private User user;
	private List<Manager> users;
	private SearchManagerFilter searchManager;

	private List<Profile> profiles;
	private Map<String, String> statusMap;

	private ManagerService managerService;

	private ReferenceDataService referenceDataService;

	private String passwordConfirmation;
	private boolean removePage;
	
	/**
	 * Prepare the data to be used on the edit page
	 * Loads position and status list
	 * @throws Exception
	 */
	public void prepareEdit() {
		loadListsForAddEditPage();
	}

	/**
	 * Prepare data to be used to display employee data
	 */
	public void prepareShow() {
		loadListsForAddEditPage();
	}
	
	/**
	 * Prepare data to be used to display employee data
	 * before removal.
	 */
	public void prepareRemove() {
		loadListsForAddEditPage();
	}
	
	/**
	 * Prepare the data to be used on the add page
	 * Loads position and status list
	 * @throws Exception
	 */
	public void prepareAdd(){
		loadListsForAddEditPage();
	}
	
	/**
	 * Prepare the data to be used on the add page
	 * We should preload the list needed to render the add page.
	 * When a validation fails the application goes back to the add page and this data is needed.
	 * Loads position and status list
	 * @throws Exception
	 */
	public void prepareSave(){
		loadListsForAddEditPage();
	}

	/**
	 * Retrieves all the employees that belong to the user's store.
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		setUsers(getUserList());
		setExtraInformation();
		return SpmActionResult.LIST.getResult();
	}

	/**
	 * Shows the information of a selected user
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		loadUserById();
		setExtraInformation();
		return SpmActionResult.SHOW.getResult();
	}
	
	public String remove() throws Exception {
		loadUserById();
		setRemovePage(true);
		setUserId(getManager().getId());
		setExtraInformation();
		return SpmActionResult.SHOW.getResult();
	}
	
	public String delete() throws Exception {		
		//Getting user
		Manager auxManager = getUserById();
		getManagerService().delete(auxManager);
		setExtraInformation();
		return SpmActionResult.LISTACTION.getResult();
	}	
	
	public String edit() throws Exception {
		loadUserById();
		setExtraInformation();
		return SpmActionResult.EDIT.getResult();
	}
	
	public String add() throws Exception {
		setExtraInformation();
		return SpmActionResult.EDIT.getResult();
	}

	public String save() throws Exception {
		try {
			setSaveObject();
			getManagerService().save(getManager());
			setExtraInformation();
			return SpmActionResult.LISTACTION.getResult();
			
		} catch (SpmCheckedException e) {
			addActionError(e.getErrorMessage());
		}
		
		return SpmActionResult.EDIT.getResult();
	}
	
	public String search(){
		setCriteria();
		setUsers(getManagerService().filterUser(getSearchManager()));
		return SpmActionResult.LIST.getResult();
	}

	private void loadListsForAddEditPage() {
		setStatusMap(getReferenceDataService().getStatus());
	}
	
	private void loadUserById(){
		setManager(getUserById());
		setUser((User)getManager());
		setPasswordConfirmation(getUser().getPassword());
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the user
	 */
	public Manager getManager() {
		return manager;
	}

	/**
	 * @param user the user to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
	 * @return the userService
	 */
	public ManagerService getManagerService() {
		return managerService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

	/**
	 * @return the referenceDataService
	 */
	public ReferenceDataService getReferenceDataService() {
		return referenceDataService;
	}

	/**
	 * @param referenceDataService the referenceDataService to set
	 */
	public void setReferenceDataService(ReferenceDataService referenceDataService) {
		this.referenceDataService = referenceDataService;
	}

	/**
	 * @return the profiles
	 */
	public List<Profile> getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}


	/**
	 * @return the users
	 */
	public List<Manager> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<Manager> users) {
		this.users = users;
	}

	/**
	 * @return the statusMap
	 */
	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	/**
	 * @param statusMap the statusMap to set
	 */
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	/**
	 * @return the passwordConfirmation
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * @param passwordConfirmation the passwordConfirmation to set
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * @return the removePage
	 */
	public boolean isRemovePage() {
		return removePage;
	}

	/**
	 * @param removePage the removePage to set
	 */
	public void setRemovePage(boolean removePage) {
		this.removePage = removePage;
	}

	
	/**
	 * @return the paramId
	 */
	public Integer getParamId() {
		return paramId;
	}

	/**
	 * @param paramId the paramId to set
	 */
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the searchManager
	 */
	public SearchManagerFilter getSearchManager() {
		return searchManager;
	}

	/**
	 * @param searchManager the searchManager to set
	 */
	public void setSearchManager(SearchManagerFilter searchManager) {
		this.searchManager = searchManager;
	}

	protected abstract List<Manager>getUserList();
	protected abstract void setSaveObject();
	protected abstract void setExtraInformation();
	protected abstract Manager getUserById();
	protected abstract void setCriteria();
}
