package br.com.application.authenticationauthorization.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class InMemorySecurityConfig {

    @Autowired
    public void configureGlobalConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("user01").password("{noop}123").roles("ADM")
                .and()
                    .withUser("user02").password("{noop}123").roles("OPERATOR");
    }
}
