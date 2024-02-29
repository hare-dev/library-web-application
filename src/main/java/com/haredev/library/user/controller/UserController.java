package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.VerificationTokenResponse;
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

    @GetMapping("/users/{id}")
    ResponseEntity<?> findUserById(@PathVariable final Long id) {
        Either<UserError, UserDetailsDto> response = userFacade.findUserById(id);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> removeUserById(@PathVariable final Long id) {
        userFacade.removeUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/promote/{id}")
    ResponseEntity<?> promoteToAdmin(@PathVariable final Long id) {
        Either<UserError, UserDetailsDto> response = userFacade.promoteToAdmin(id);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> changeUsername(@PathVariable final Long id, @RequestParam final String newUsername) {
        Either<UserError, UserDetailsDto> response = userFacade.changeUsername(id, newUsername);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("users/confirmation/{id}")
    ResponseEntity<?> createConfirmationToken(@PathVariable final Long id) {
        Either<UserError, VerificationTokenResponse> response = userFacade.createConfirmationToken(id);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("users/registration/confirm/{id}")
    ResponseEntity<?> confirmRegistration(@RequestParam final String token, @PathVariable final Long id) {
        Either<UserError, UserDetailsDto> response = userFacade.confirmRegistration(token, id);
        return ResponseResolver.resolve(response);
    }
}
