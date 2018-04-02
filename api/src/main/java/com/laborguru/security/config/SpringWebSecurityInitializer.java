package com.laborguru.security.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by federicobarreraoro on 01/30/17.
 */
public class SpringWebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {


    public SpringWebSecurityInitializer() {
        super(WebSecurityConfig.class);
    }

}
