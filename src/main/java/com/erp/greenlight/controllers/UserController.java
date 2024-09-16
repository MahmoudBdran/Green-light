package com.erp.greenlight.controllers;

import com.erp.greenlight.DTOs.UserDto;
import com.erp.greenlight.services.UserService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost", "https://animated-sprinkles-d16b69.netlify.app"})

@RequestMapping("/users")

public class UserController {

    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<Object> findAll(){
        return AppResponse.generateResponse("all_data", HttpStatus.OK, userService.findAll() , true);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UserDto user){
       return AppResponse.generateResponse("تم حفط الحساب بنجاح", HttpStatus.OK, userService.save(user) , true);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody UserDto user){
        return AppResponse.generateResponse("تم حفط الحساب بنجاح", HttpStatus.OK, userService.update(user) , true);
    }

}
