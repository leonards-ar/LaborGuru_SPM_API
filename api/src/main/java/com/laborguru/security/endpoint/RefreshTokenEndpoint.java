package com.laborguru.security.endpoint;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laborguru.model.Profile;
import com.laborguru.model.User;
import com.laborguru.security.auth.jwt.extractor.TokenExtractor;
import com.laborguru.security.auth.jwt.verifier.TokenVerifier;
import com.laborguru.security.config.JwtSettings;
import com.laborguru.security.config.WebSecurityConfig;
import com.laborguru.security.exceptions.InvalidJwtToken;
import com.laborguru.security.model.UserContext;
import com.laborguru.security.model.token.JwtToken;
import com.laborguru.security.model.token.RawAccessJwtToken;
import com.laborguru.security.model.token.RefreshToken;
import com.laborguru.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
public class RefreshTokenEndpoint {
    @Autowired private com.laborguru.security.model.token.JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey());
        if(refreshToken == null){
            throw new InvalidJwtToken();
        }

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        User user = new User();
        user.setUserName(refreshToken.getSubject());

        User foundUser = userService.getUserByUserName(user);
        if(foundUser == null) {
            throw new UsernameNotFoundException("User not found: " + user.getUserName());
        }

        if (foundUser.getProfiles() == null || foundUser.getProfiles().size() > 0) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = new LinkedList();
        for(Profile authority: foundUser.getProfiles()) {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        UserContext userContext = UserContext.create(foundUser.getUserName(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
