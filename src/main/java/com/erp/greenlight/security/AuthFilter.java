package com.erp.greenlight.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2

public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userService;
    @Autowired
    private JwtTokenUtils tokenUtil;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String jwtTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("Path is >> " + request.getRequestURL());
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        if (jwtTokenHeader != null && securityContext.getAuthentication() == null) {
            String jwtToken = jwtTokenHeader.substring("Bearer ".length());
            if (tokenUtil.validateToken(jwtToken, request)) {
                String username = tokenUtil.getUserNameFromToken(jwtToken);
                if (username != null) {
                    AppUserDetail userDetails = (AppUserDetail) userService.loadUserByUsername(username);

                    if (tokenUtil.isTokenValid(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }


}