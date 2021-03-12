package com.epam.myhotels.hotels.configs;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Setter
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        String whitelistUrlsStr = environment.getProperty("app.security.whitelisted.urls");
        assert whitelistUrlsStr != null;
        String[] whitelistUrls = whitelistUrlsStr.split(",");

        web.ignoring().antMatchers(Arrays.stream(whitelistUrls).filter(str -> !str.isBlank()).map(String::trim)
                                         .toArray(String[]::new));
    }
}