package com.haredev.library.security.controller;

import com.haredev.library.security.dto.LoginCredentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials loginCredentials) {}
}
