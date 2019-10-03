package com.crmwebapp.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/css/**", "/index","/product/list").permitAll()		
			.antMatchers("/*/delete").hasRole("ADMIN")
			
			.antMatchers("/customer/list","/purchase/list").hasAnyRole("OFFICER","ADMIN")//hasRole("USER")
			.antMatchers("/product/**").hasAnyRole("OFFICER","ADMIN")			
			.antMatchers("/customer/**").hasAnyRole("OFFICER","ADMIN")
			.antMatchers("/purchase/**").hasRole("ADMIN")
			
			/*REST Service Permissions*/
			.antMatchers(HttpMethod.GET, "/products/*").permitAll()
			.antMatchers(HttpMethod.GET, "/customers/*","/purchases/*").hasAnyRole("OFFICER","ADMIN")
			.antMatchers(HttpMethod.POST, "/customers/*","/products/*").hasAnyRole("OFFICER","ADMIN")
			.antMatchers(HttpMethod.PUT, "/customers/*","/products/*").hasAnyRole("OFFICER","ADMIN")
			.antMatchers(HttpMethod.POST, "/purchases/*").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/purchases/*").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/customers/*","/products/*","/purchases/*").hasAnyRole("ADMIN")
			/**************************/
			
			.antMatchers("/actuator/health","/actuator/info").permitAll()
			.antMatchers("/actuator/**").hasRole("ADMIN")
			
			
			/*// Precedente
			.and() .formLogin().permitAll() .and() .logout().permitAll();*/
				 
		
			.and()
				.formLogin()
				.loginPage("/showLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);		
	}
}