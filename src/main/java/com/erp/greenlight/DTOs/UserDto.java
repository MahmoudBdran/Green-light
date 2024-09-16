package com.erp.greenlight.DTOs;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Long role;
}
