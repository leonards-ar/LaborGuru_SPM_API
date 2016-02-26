package com.laborguru.action.user;

import java.util.List;
import java.util.Map;

import com.laborguru.action.SpmAction;
import com.laborguru.action.SpmActionResult;
import com.laborguru.exception.SpmCheckedException;
import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.service.data.ReferenceDataService;
import com.laborguru.service.profile.ProfileService;
import com.laborguru.service.user.UserService;

/**
 *
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public abstract class UserBaseAction extends SpmAction {

	private Integer paramId;
	
	private Integer userId;
	private User user;
	private List<User> users;
	
	private List<Profile> profiles;
	private Map<String, String> statusMap;

	private UserService userService;
	private ProfileService profileService;
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
		return SpmActionResult.SHOW.getResult();
	}
	
	public String remove() throws Exception {
		loadUserById();
		setRemovePage(true);
		return SpmActionResult.SHOW.getResult();
	}
	
	public String delete() throws Exception {		
		//Getting user
		User auxUser = getUserService().getUserById(getUser());		
		getUserService().logicalDelete(auxUser);
		
		return SpmActionResult.LISTACTION.getResult();
	}	
	
	public String edit() throws Exception {
		loadUserById();
		return SpmActionResult.EDIT.getResult();
	}
	
	public String add() throws Exception {
		return SpmActionResult.EDIT.getResult();
	}

	public String save() throws Exception {
		try {
			
			addUserProfile();
			getUserService().save(getUser());
			return SpmActionResult.LISTACTION.getResult();
			
		} catch (SpmCheckedException e) {
			addActionError(e.getErrorMessage());
		}
		
		return SpmActionResult.EDIT.getResult();
	}
	

	
	private void loadListsForAddEditPage() {
		setProfiles(getProfileService().findAll());
		setStatusMap(getReferenceDataService().getStatus());
	}

	private void loadUserById() {
		User tmpUser = new User();
		tmpUser.setId(userId);
		setUser(userService.getUserById(tmpUser));
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
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the profileService
	 */
	public ProfileService getProfileService() {
		return profileService;
	}

	/**
	 * @param profileService the profileService to set
	 */
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
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
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
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

	protected abstract List<User>getUserList();
	protected abstract void addUserProfile();
	protected abstract void setExtraInformation();
}
