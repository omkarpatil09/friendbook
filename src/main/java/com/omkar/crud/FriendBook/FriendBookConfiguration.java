package com.omkar.crud.FriendBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class FriendBookConfiguration extends WebSecurityConfigurerAdapter
{

	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception 
	{
        auth.authenticationProvider(authProvider);
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
	    web.ignoring().antMatchers("/friendbook/*/signup");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception 
	{
		//http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().httpBasic();
		http.csrf().disable().authorizeRequests()
		.anyRequest().authenticated()
		.and().httpBasic();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
	}
}
