package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @PostMapping("/registration/user")
    public ResponseEntity<?> registerAsUser(@RequestBody RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsUser(request));
    }

    @PostMapping("/registration/admin")
    public ResponseEntity<?> registerAsAdmin(@RequestBody RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsAdmin(request));
    }

    @GetMapping("/users")
    ResponseEntity<?> fetchAllUsers(@RequestParam(required = false) Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        List<UserDto> response = userFacade.fetchAllUsers(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<?> findUserById(@PathVariable Long userId) {
        Either<UserError, UserDto> response = userFacade.findUserById(userId);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/users/{userId}")
    HttpStatus removeBook(@PathVariable Long userId) {
        userFacade.removeUserById(userId);
        return HttpStatus.OK;
    }
}
