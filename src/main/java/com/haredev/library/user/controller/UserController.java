package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.validation.RegistrationRequest;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @PostMapping("/registration/user")
    public ResponseEntity registerAsUser(@RequestBody RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsUser(request));
    }

    @PostMapping("/registration/admin")
    public ResponseEntity registerAsAdmin(@RequestBody RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsAdmin(request));
    }

    @GetMapping("/users")
    public ResponseEntity fetchAllUsers() {
        List<UserDto> response = userFacade.fetchAllUsers();
        return ResponseResolver.resolve(response);
    }
}
