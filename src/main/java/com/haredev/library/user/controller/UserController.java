package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.error.UserError;
import com.haredev.library.user.domain.dto.UserPublicDetailsDto;
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

    @GetMapping("/users")
    ResponseEntity<?> fetchAllUsers(@RequestParam(required = false) final Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        List<UserPublicDetailsDto> response = userFacade.fetchAllUsers(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?> findUserById(@PathVariable final Long id) {
        Either<UserError, UserPublicDetailsDto> response = userFacade.findUserById(id);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> removeUserById(@PathVariable final Long id) {
        userFacade.removeUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/promote/{id}")
    ResponseEntity<?> promoteToAdmin(@PathVariable final Long id) {
        Either<UserError, UserPublicDetailsDto> response = userFacade.promoteToAdmin(id);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> changeUsername(@PathVariable final Long id, @RequestParam final String newUsername) {
        Either<UserError, UserPublicDetailsDto> response = userFacade.changeUsername(id, newUsername);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("users/registration/confirmation")
    ResponseEntity<?> confirmRegistration(@RequestParam final String token) {
        Either<UserError, UserPublicDetailsDto> response = userFacade.confirmRegistration(token);
        return ResponseResolver.resolve(response);
    }
}
