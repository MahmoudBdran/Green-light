package com.erp.greenlight.controllers;


import com.erp.greenlight.DTOs.AccessTokenDto;
import com.erp.greenlight.DTOs.LoginRequestDto;
import com.erp.greenlight.services.AuthService;
import com.erp.greenlight.utils.AppResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login (@RequestBody LoginRequestDto loginRequest){

        return new ResponseEntity<>(authService.login(loginRequest.getUsername(), loginRequest.getPassword()), HttpStatus.OK);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenDto> refreshAccessToken(@RequestParam String refreshToken) {
        AccessTokenDto dto = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestParam String refreshToken) {
        authService.logoutUser(refreshToken);
        return AppResponse.generateResponse("logout success", HttpStatus.OK, null, true);

    }
}
