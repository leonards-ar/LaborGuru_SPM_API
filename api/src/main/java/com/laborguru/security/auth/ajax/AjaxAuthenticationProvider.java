package com.laborguru.security.auth.ajax;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.laborguru.security.model.UserContext;


/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    public AjaxAuthenticationProvider(final UserService userService) {
        this.userService = userService;
        this.encoder = null;
    }
    @Autowired
    public AjaxAuthenticationProvider(final UserService userService, final BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = new User();
        user.setUserName(username);

        User foundUser = userService.getUserByUserName(user);
        if(foundUser == null) {
            throw new UsernameNotFoundException("User not found: " + user.getUserName());
        }
        
        if (!equalsPassword(password, foundUser.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (user.getProfiles() == null || user.getProfiles().size() == 0) throw new InsufficientAuthenticationException("User has no roles assigned");
        
        List<GrantedAuthority> authorities = new LinkedList();
        for(Profile authority: user.getProfiles()) {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        }

        UserContext userContext = UserContext.create(foundUser.getUserName(), authorities);
        
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private boolean equalsPassword(String expected, String actual){
        if(encoder != null){
            return encoder.matches(expected, actual);
        }

        return actual.equals(expected);
    }
}
