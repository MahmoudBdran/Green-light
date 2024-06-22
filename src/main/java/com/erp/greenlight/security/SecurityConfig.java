//package com.erp.greenlight.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
//////                .authorizeRequests()
//////                .requestMatchers("/login", "/h2-console/**").permitAll()
//////                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//////                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
//////                .anyRequest().authenticated();
//////
//////        return http.build();
////        http.authorizeRequests(configurer-> configurer.anyRequest().authenticated())
////                .formLogin(form->form.loginPage("/login").permitAll()
////                        .loginProcessingUrl("/authenticateTheUser").permitAll());
////        return http.build();
//    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails john = User.builder()
//                .username("john")
//                .password("{noop}test123")
//                .roles("EMPLOYEE")
//                .build();
//        return new InMemoryUserDetailsManager(john);
//    }
//
//}
