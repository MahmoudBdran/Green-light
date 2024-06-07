package com.erp.greenlight.config;

import com.erp.greenlight.models.Admin;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;



public class AuditorAwareImpl implements AuditorAware<Admin> {

    @Override
    public Optional<Admin> getCurrentAuditor() {
        return Optional.of(new Admin(1));
    }
}
