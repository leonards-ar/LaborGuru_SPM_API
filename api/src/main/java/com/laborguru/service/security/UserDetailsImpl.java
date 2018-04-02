package com.laborguru.service.security;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import com.laborguru.model.Permission;
import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.model.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails, Principal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public UserDetailsImpl(User user){
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority>granters = new HashSet<GrantedAuthority>();
		Set<Profile> profileSet;
		profileSet = user.getProfiles();
		for(Profile profile: profileSet) {
			granters.add(new SimpleGrantedAuthority(profile.getName()));
			Set<Permission> permissionSet = profile.getPermissions();
			for(Permission permission: permissionSet) {
				granters.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}

		return granters;
	}

	public String getPassword() {
		
		return user.getPassword();
	}

	public String getUsername() {
		return user.getUserName();
	}
	
	public String getName() {
		return user.getUserName();
	}

	public Integer getLoginCount(){
		return user.getLoginCount();
	}

	public void setLoginCount(int count){
		user.setLoginCount(count);
	}

    public void setUserStatus(UserStatus status){
        user.setUserStatus(status);
    }

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
