package com.haredev.library.user.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
class UserController {
    private final UserFacade userFacade;

    @PostMapping("/registration")
    public ResponseEntity<?> registerAsUser(@RequestBody @Valid final RegistrationRequest request) {
        return ResponseResolver.resolve(userFacade.registerAsUser(request));
    }

    @GetMapping
    ResponseEntity<?> fetchAllUsers(@RequestParam(required = false) final Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        var response = userFacade.fetchAllUsers(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findUserById(@PathVariable final Long id) {
        var response = userFacade.findUserById(id);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> removeUserById(@PathVariable final Long id) {
        userFacade.removeUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/promote/{id}")
    ResponseEntity<?> promoteToAdmin(@PathVariable final Long id) {
        var response = userFacade.promoteToAdmin(id);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> changeUsername(@PathVariable final Long id, @RequestParam final String newUsername) {
        var response = userFacade.changeUsername(id, newUsername);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/registration/confirmation")
    ResponseEntity<?> confirmRegistration(@RequestParam final String token) {
        var response = userFacade.confirmRegistration(token);
        return ResponseResolver.resolve(response);
    }
}
