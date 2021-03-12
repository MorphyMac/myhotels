package com.epam.myhotels.api.users.configs;

import com.epam.myhotels.api.users.security.LoginFilter;
import com.epam.myhotels.api.users.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Setter
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll().anyRequest().authenticated();
        http.headers().frameOptions().disable();
        http.addFilter(loginFilter());
    }

    private Filter loginFilter() throws Exception {
        LoginFilter filter = new LoginFilter(authenticationManager(), userService, environment);
        filter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return filter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String whitelistUrlsStr = environment.getProperty("app.security.whitelisted.urls");
        assert whitelistUrlsStr != null;
        String[] whitelistUrls = whitelistUrlsStr.split(",");

        web.ignoring().antMatchers(Arrays.stream(whitelistUrls).filter(str -> !str.isBlank()).map(String::trim)
                                         .toArray(String[]::new));
    }
}