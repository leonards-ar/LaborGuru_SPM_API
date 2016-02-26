package com.laborguru.action.user;

import java.util.List;

import com.laborguru.action.SpmActionResult;
import com.laborguru.model.User;
import com.laborguru.model.filter.SearchUserFilter;
import com.opensymphony.xwork2.Preparable;

/**
 * CRUD for users that are not employees
 * @author <a href="fbarreraoro@gmail.com">Federico Barrera Oro</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
@SuppressWarnings("serial")
public class UserPrepareAction extends UserBaseAction implements Preparable{

	private SearchUserFilter searchUser;

	/**
	 * Prepare data to be used in the actions methods defined for this action
	 * @throws Exception
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		//It's needed by the Preparable interface, don't comment out or removed
	}

	/**
	 * 
	 * @return
	 * @see com.laborguru.action.user.UserBaseAction#getUserList()
	 */
	protected List<User> getUserList(){
		return getUserService().findUsersByProfile(getReferenceDataService().getAdministratorRole());
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String search() throws Exception {
		getSearchUser().setProfile(getReferenceDataService().getAdministratorRole());
		setUsers(getUserService().filterUser(getSearchUser()));
		return SpmActionResult.LIST.getResult();
	}
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.user.UserBaseAction#addUserProfile()
	 */
	protected void addUserProfile(){
		getUser().setProfile(getReferenceDataService().getAdministratorRole());
	}
	
	/**
	 * 
	 * 
	 * @see com.laborguru.action.user.UserBaseAction#setExtraInformation()
	 */
	protected void setExtraInformation(){
	}
	
	/**
	 * @return the searchUser
	 */
	public SearchUserFilter getSearchUser() {
		return searchUser;
	}

	/**
	 * @param searchUser the searchUser to set
	 */
	public void setSearchUser(SearchUserFilter searchUser) {
		this.searchUser = searchUser;
	}

}
