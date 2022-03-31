package com.example.demo.configurations;

import com.example.demo.configurations.filters.SecurityFilter;
import com.example.demo.services.EmployeeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    EmployeeDetailService myuserdetailservice;

    @Autowired
    SecurityFilter securityFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myuserdetailservice).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests().antMatchers("/home/**", "/h2-console", "/h2-console/**", "/authenticate", "/register", "/", "/static/**", "/manifest.json", "/favicon.ico", "/logo192.png", "/swagger-ui.html/**", "/v3/**", "/swagger-ui/**", "/swagger/**", "/get/**").permitAll().anyRequest().authenticated().and().sessionManagement()//
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://localhost:3000");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
////			CorsConfiguration c2 = new CorsConfiguration();
////			c2.setAllowCredentials(true);
////			c2.addAllowedOrigin("http://127.0.0.1:5500");
////			c2.addAllowedHeader("*");
////			c2.addAllowedMethod("*");
////			source.registerCorsConfiguration("/**", c2);
//		return new CorsFilter(source);
//	}

}
