package com.infy.micro.assignment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



	
	private AuthenticationManager authenticationManager;

	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/api/rest/**").authenticated()
		.antMatchers("/nonprotectedurl").permitAll();
		http.addFilterBefore(customUsernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(customAuthenticationProvider());

	}
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		return  provider;
	}

@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
}
	
	@Bean
	public CustomUsernamePasswordFilter customUsernamePasswordFilter() throws Exception {
		
		CustomUsernamePasswordFilter custom = new CustomUsernamePasswordFilter() ;
		custom.setAuthenticationManager(authenticationManagerBean());
		return custom;
	}

	
	@Bean
	public UserDetailsServiceImpl userDetailsService() {
		return new UserDetailsServiceImpl();
	}
}
