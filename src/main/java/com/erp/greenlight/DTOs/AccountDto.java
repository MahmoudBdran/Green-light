package com.erp.greenlight.DTOs;

import com.erp.greenlight.models.Account;
import com.erp.greenlight.models.AccountType;
import com.erp.greenlight.models.Admin;
import com.erp.greenlight.models.Customer;
import jakarta.persistence.*;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data

public class AccountDto {

    private Long id;
    private String name;
    private Boolean isParent;


    private int startBalanceStatus;
    private BigDecimal startBalance;
    private BigDecimal currentBalance = BigDecimal.ZERO;
    private AdminDto addedBy;
    private AdminDto updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active=true;

    public static AccountDto convertToDto(Customer customer) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(customer, AccountDto.class);
    }
}
