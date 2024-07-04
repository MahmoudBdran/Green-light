package com.erp.greenlight.config;

import com.erp.greenlight.models.Admin;
import com.erp.greenlight.repositories.AdminRepository;
import com.erp.greenlight.services.AuthService;
import com.erp.greenlight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;



public class AuditorAwareImpl implements AuditorAware<Admin> {

    @Autowired
    private UserService userService;

    @Override
    public Optional<Admin> getCurrentAuditor() {


        return Optional.of( userService.getCurrentAuthUser());
    }
}
