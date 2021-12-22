package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/**").hasAuthority("Admin")
                .antMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/brands/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/products/new", "product/delete/**")
                .hasAnyAuthority("Admin", "Editor")
                .antMatchers("/products/edit/**", "/product/save", "/product/check_unique")
                .hasAnyAuthority("Admin", "Editor", "Salesperson")
                .antMatchers("/products", "/products/", "/product/detail/**", "product/page/**")
                .hasAnyAuthority("Admin", "Salesperson", "Editor", "Shipper")
                .antMatchers("/customers/**").hasAnyAuthority("Admin", "Salesperson")
                .antMatchers("/shipping/**").hasAnyAuthority("Admin", "Salesperson")
                .antMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
                .antMatchers("/reports/**").hasAnyAuthority("Admin", "Salesperson")
                .antMatchers("/articles/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/menus/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/settings/**").hasAuthority("Admin")
                .anyRequest().authenticated()
                .and().formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login")
                .permitAll()
                .and().logout()
                .permitAll()
                .and().rememberMe()
                .key("keyFixed")
                .tokenValiditySeconds(7 * 24 * 60 * 60);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/webjars/**");
    }
}
