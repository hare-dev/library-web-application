package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.validation.RegistrationRequest;
import com.haredev.library.user.controller.validation.RegistrationResponse;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDto;
import io.vavr.control.Either;
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

    @PostMapping("admin/registration")
    public Either<UserError, RegistrationResponse> registerAsUser(@RequestBody RegistrationRequest request) {
        return userFacade.registerAsUser(request);
    }

    @PostMapping("/user/registration")
    public Either<UserError, RegistrationResponse> registerAsAdmin(@RequestBody RegistrationRequest request) {
        return userFacade.registerAsAdmin(request);
    }

    @GetMapping("/user")
    public ResponseEntity fetchAllUsers() {
        List<UserDto> response = userFacade.fetchAllUsers();
        return ResponseResolver.resolve(response);
    }
}
