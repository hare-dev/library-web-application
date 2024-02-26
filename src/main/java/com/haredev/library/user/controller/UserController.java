package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.ConfirmationTokenResponse;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @PostMapping("/registration/user")
    public ResponseEntity<?> registerAsUser(@RequestBody @Valid final RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsUser(request));
    }

    @PostMapping("/registration/admin")
    public ResponseEntity<?> registerAsAdmin(@RequestBody @Valid final RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsAdmin(request));
    }

    @GetMapping("/users")
    ResponseEntity<?> fetchAllUsers(@RequestParam(required = false) final Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        List<UserDetailsDto> response = userFacade.fetchAllUsers(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<?> findUserById(@PathVariable final Long userId) {
        Either<UserError, UserDetailsDto> response = userFacade.findUserById(userId);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<?> removeUser(@PathVariable final Long userId) {
        userFacade.removeUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/promote/{userId}")
    ResponseEntity<?> promoteToAdmin(@PathVariable final Long userId) {
        Either<UserError, UserDetailsDto> response = userFacade.promoteToAdmin(userId);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/users/{userId}")
    ResponseEntity<?> changeUsername(@PathVariable final Long userId, @RequestParam final String username) {
        Either<UserError, UserDetailsDto> response = userFacade.changeUsername(userId, username);
        return ResponseResolver.resolve(response);
    }

    @PostMapping("users/confirmation/{userId}")
    ResponseEntity<?> createConfirmationToken(@PathVariable final Long userId) {
        Either<UserError, ConfirmationTokenResponse> response = userFacade.createConfirmationToken(userId);
        return ResponseResolver.resolve(response);
    }
}
