package com.laborguru.acegi.granter;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.jaas.AuthorityGranter;
import org.acegisecurity.userdetails.UserDetails;

public class UserGranterAuthority implements AuthorityGranter {

	public Set<String> grant(Principal principal) {
		UserDetails userDetails = (UserDetails)principal;
		GrantedAuthority[] grants = userDetails.getAuthorities();
		if(grants == null || grants.length == 0) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		for(int i = 0; i < grants.length; i++) {
			set.add(grants[i].getAuthority());
		}
		return set;
	}
}
