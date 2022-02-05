package com.enatcpromanager.com.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	BCryptPasswordEncoder bCryptEncoder;

    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
        		.usersByUsernameQuery("select username, password, enabled from user_accounts where username = ?")
        		.authoritiesByUsernameQuery("select username, role from user_accounts where username = ?")
        		.passwordEncoder(bCryptEncoder);
    }



    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/projects/new").hasAnyRole("ADMIN", "PROJADMIN")
                .antMatchers("/employees/new").hasAnyRole("ADMIN", "EMPADMIN")
                .antMatchers("/certifications/new").hasRole("ADMIN")
                .antMatchers("/employees/update").hasAnyRole("ADMIN", "EMPADMIN")
                .antMatchers("/projects/update").hasAnyRole("ADMIN", "PROJADMIN")
                .antMatchers("/employees/save").hasAnyRole("ADMIN", "EMPADMIN")
                .antMatchers("/projects/save").hasAnyRole("ADMIN", "PROJADMIN")
                .antMatchers("/projects/delete").hasRole("ADMIN")
                .antMatchers("/employees/delete").hasRole("ADMIN")
                .antMatchers("/").authenticated().and().formLogin();


    }
}

