package com.erp.greenlight.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class InvalidUserOrPass extends  RuntimeException{
    public InvalidUserOrPass() {
    }

    public InvalidUserOrPass(String message) {
        super(message);
    }

}