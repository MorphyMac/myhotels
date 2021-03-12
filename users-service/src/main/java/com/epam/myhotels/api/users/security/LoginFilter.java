package com.epam.myhotels.api.users.security;

import com.epam.myhotels.api.users.dto.LoginDto;
import com.epam.myhotels.api.users.model.UserModel;
import com.epam.myhotels.api.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;

    public LoginFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

            UsernamePasswordAuthenticationToken customAuthToken = new UsernamePasswordAuthenticationToken(loginDto
                    .getEmail(), loginDto.getPassword(), new ArrayList<>());

            return getAuthenticationManager().authenticate(customAuthToken);
        } catch (IOException ioe) {
            log.error("Unable to authenticate login request.", ioe);
            throw new RuntimeException(ioe);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserModel userModel = userService.findByEmail(username);

        Date expiryTime = Date.from(Instant.now().plusSeconds(Long
                .getLong(environment.getProperty("jwt.token.expiration_time"), 1000 * 60)));
        String jwtToken = Jwts.builder().setSubject(userModel.getUserId()).setExpiration(expiryTime)
                              .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.token.secret"))
                              .compact();

        response.addHeader("token", jwtToken);
        response.addHeader("user-id", userModel.getUserId());
    }
}