package com.skyfenko.web.config;

import com.skyfenko.web.constants.URIConstants;
import com.skyfenko.web.handler.AppAccessDeniedHandler;
import com.skyfenko.web.handler.AppAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", URIConstants.Flow.ACCESS_DENIED, "/swagger-resources").permitAll()
                .antMatchers(URIConstants.Api.STOCKS, URIConstants.Flow.LOGOUT, URIConstants.Flow.DASHBOARD)
                .hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(URIConstants.Flow.LOGIN).permitAll().successHandler(authenticationSuccessHandler()).defaultSuccessUrl(URIConstants.Flow.DASHBOARD)
                .and()
                .logout().logoutUrl(URIConstants.Flow.LOGOUT).permitAll().logoutSuccessUrl(URIConstants.Flow.LOGIN)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AppAccessDeniedHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("$2a$10$4oEx/i2sQsH9xBtdAFxmxeOoiGq7r2MZDYdWIsoc.7DRcNUE8unTG").roles("USER")
                .and()
                .passwordEncoder(passwordEncoder());
    }
}