package com.erp.greenlight.DTOs;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.Admin;
import com.erp.greenlight.models.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data

public class CustomerDto {

    private Long id;


    private String name;
    private AccountDto account;

    private int startBalanceStatus;

    private BigDecimal startBalance;

    private BigDecimal currentBalance = BigDecimal.ZERO;

    private String notes;

    private AdminDto addedBy;

    private AdminDto updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean active;

    private String address;

    private String phones;

    public static CustomerDto convertToDto(Customer customer) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(customer, CustomerDto.class);
    }
}
