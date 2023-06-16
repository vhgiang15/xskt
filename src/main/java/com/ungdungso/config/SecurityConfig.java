package com.ungdungso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
public class SecurityConfig {
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/login/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/js/**").permitAll()
						.requestMatchers("/user**").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/admin**").hasAnyRole("ADMIN")
						.anyRequest().permitAll())
				.formLogin((form) -> form
						.loginPage("/login-form")
						.loginProcessingUrl("/loginpost")
						.successHandler(loginSuccessHandler)
						.failureUrl("/login-form?error=failed")
						.permitAll())
				.logout((logout) -> logout
						.logoutSuccessUrl("/index")
						.permitAll()).exceptionHandling().accessDeniedPage("/403");
		return http.build();

	}

}
