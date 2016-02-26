package com.laborguru.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Spm User Type
 * @author <a href="cnunezre@gmail.com">Cristian Nunez Rebolledo</a>
 * @version 1.0
 * @since SPM 1.0
 *
 */
public class User extends SpmObject {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String userName;
	private String password;
	private UserStatus userStatus;
	private String email;
	private String name;
	private String surname;
	private Date lastLogon;
	private Integer loginCount;
	private Date creationDate;
	private Date lastUpdateDate;
	private Set<Profile> profiles;
	
	public User() {
		
	}
	
	/**
	 * 
	 * @param user
	 */
	public User(User user){
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.userStatus = user.getUserStatus();
		this.email = user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.lastLogon = user.getLastLogon();
		this.loginCount = user.getLoginCount();
		this.creationDate = user.getCreationDate();
		this.profiles = user.getProfiles();
	}
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.name)
		.append(this.surname)
		.append(this.userName)
		.toHashCode();
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;		

		final User other = (User) obj;
		
		return new EqualsBuilder().append(this.name, other.name)
		.append(this.surname, other.surname)
		.append(this.userName, this.userName)
		.isEquals();
	}


	/**
	 * User toString
	 * @return string version of the object 
	 * @see com.laborguru.model.SpmObject#toString()
	 */
	public String toString()
	{
		return new ToStringBuilder(this, DEFAULT_TO_STRING_STYLE)
	   	.append("id" , id)
	   	.append("userName",userName)
	   	.append("name",name)
	   	.append("surname", surname)
	   	.append("email", email)
	   	.toString();		
	}	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * @return
	 */
	public Set<Profile> getProfiles() {
		if(profiles == null) {
			setProfiles(new HashSet<Profile>());
		}
		return profiles;
	}
	
	
	/**
	 * We leave it private to enforce the cardinality with the addProfile.
	 * DO NOT MAKE IT PUBLIC
	 * @param profile
	 */
	private void setProfiles(Set<Profile> profile) {
		this.profiles = profile;
	}
	
	/**
	 * @param profile
	 */
	public void addProfile(Profile profile){
		
		if (profile == null){
			throw new IllegalArgumentException("Null profile passed in as parameter");
		}
		
		if(this.profiles == null) {
			this.profiles = new HashSet<Profile>();
		}

		getProfiles().add(profile);
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return getUserStatus() != null ? getUserStatus().getStatus() : UserStatus.ENABLED.getStatus();
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		if(status != null && status.intValue() >= 0 && status.intValue() < UserStatus.values().length) {
			setUserStatus(UserStatus.values()[status.intValue()]);
		} else {
			setUserStatus(UserStatus.ENABLED);
		}
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the lastLogon
	 */
	public Date getLastLogon() {
		return lastLogon;
	}
	/**
	 * @param lastLogon the lastLogon to set
	 */
	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}
	/**
	 * @return the loginCount
	 */
	public Integer getLoginCount() {
		return loginCount;
	}
	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * The user will have only one profile.
	 * But it keeps having a Set of Profiles in case of a change in specifications.
	 * @param profile
	 */
	public void setProfile(Profile profile){
		getProfiles().clear();
		getProfiles().add(profile);
	}
	
	/**
	 * 
	 * @return
	 */
	public Profile getProfile() {
		if(getProfiles() != null && !getProfiles().isEmpty()) {
			return getProfiles().iterator().next();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFullName() {
		StringBuffer fullName = new StringBuffer();
		if(getName() != null) {
			fullName.append(getName());
		}
		fullName.append(" ");
		if(getSurname() != null) {
			fullName.append(getSurname());
		}
		return fullName.toString().trim();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return UserStatus.ENABLED.equals(getUserStatus());
	}

	
	/**
	 * 
	 * @return
	 */
	public boolean isDisabled() {
		return UserStatus.DISABLED.equals(getUserStatus());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return UserStatus.DELETED.equals(getUserStatus());
	}

	/**
	 * @return the userStatus
	 */
	public UserStatus getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
