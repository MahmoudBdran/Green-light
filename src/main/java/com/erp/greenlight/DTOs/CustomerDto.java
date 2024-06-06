package com.erp.greenlight.DTOs;

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


    private Long customerCode;

    private String name;

    private Long accountNumber;

    private int startBalanceStatus;

    private BigDecimal startBalance;

    private BigDecimal currentBalance = BigDecimal.ZERO;

    private String notes;

    private AdminDto addedBy;

    private Integer updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt=null;

    private boolean active=true;

    private int comCode;

    private LocalDate date;

    private String address;

    private String phones;
    public static CustomerDto convertToDto(Customer customer) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(customer, CustomerDto.class);
    }
}
