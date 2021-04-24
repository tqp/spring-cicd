package com.timsanalytics.cicd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Environment environment;

    @Autowired
    public SpringWebSecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                .exceptionHandling().accessDeniedPage("/login-page")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .logout().logoutSuccessUrl("/login-page")

                // LOCK THE ENDPOINTS DOWN HERE
                .and()
                .authorizeRequests()
//                .antMatchers("**").permitAll() // WIDE OPEN!!!

                // Front-End Resources
                .antMatchers("/*", "/*/*").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()

                // API Endpoints OPEN
//                .antMatchers("/app/**").permitAll()
                .antMatchers("/api/v1/diagnostics/**").permitAll()
                .antMatchers("/api/v1/health-check/**").permitAll()

                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/csrf",
                "/v2/api-docs",
                "/api-docs/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
