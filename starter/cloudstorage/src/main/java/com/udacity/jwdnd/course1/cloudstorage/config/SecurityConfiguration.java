package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticatedService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AuthenticatedService authenticatedService;

    public SecurityConfiguration(AuthenticatedService authenticatedService) {
        this.authenticatedService = authenticatedService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticatedService);
    }

    /*
     * configure don't allow to access resource
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    /*
     * configure authorization
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/resources/**","/login", "signup","/error").permitAll()
                    .antMatchers("/home","/home/**").authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                    .and()
                        .logout();
    }
}
