package com.github.fish56.resttokensecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenFilter tokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("正在加载配置文件");
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/token").permitAll()
                .antMatchers("/home").authenticated()
                .antMatchers("/vip").hasAuthority("VIP")
                .antMatchers("/admin").hasAuthority("ADMIN");

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
