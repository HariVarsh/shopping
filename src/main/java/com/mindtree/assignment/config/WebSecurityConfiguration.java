package com.mindtree.assignment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
	public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	       http.csrf().disable()
	        .authorizeRequests().antMatchers(HttpMethod.OPTIONS,"*/").permitAll()
            .antMatchers(HttpMethod.GET,"/login").permitAll();
//	        .antMatchers(HttpMethod.POST,"/cart/register").permitAll()
//	        .antMatchers(HttpMethod.POST, "/login").permitAll()
//	        .antMatchers(HttpMethod.POST,"/newuser/*").permitAll()
//	        .antMatchers(HttpMethod.GET,"/master/*").permitAll()
//	         .antMatchers(HttpMethod.GET,"/exploreCourse").permitAll()
//	        .anyRequest().authenticated();	
	    }
	}

