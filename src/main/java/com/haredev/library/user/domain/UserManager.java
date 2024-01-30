package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RequiredArgsConstructor
class UserManager {
    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final UserFactory userFactory;
    private static final int pageSize = 20;

    public Option<UserApplication> getUserByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username));
    }

    public Either<UserError, RegistrationResponse> registerUser(RegistrationRequest userRequest) {
        return userValidation.validateParameters(userRequest)
                .map(this::createUser);
    }

    public Either<UserError, RegistrationResponse> registerAdmin(RegistrationRequest userRequest) {
        return userValidation.validateParameters(userRequest)
                .map(this::createAdmin);
    }

    public RegistrationResponse createUser(RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildUser(request);
        return userRepository.save(userApplication).toRegistrationResponse();
    }

    public RegistrationResponse createAdmin(RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildAdmin(request);
        return userRepository.save(userApplication).toRegistrationResponse();
    }

    public List<UserApplication> fetchAllUsersWithPageable(int page) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Option<UserApplication> findById(Long userId) {
        return Option.ofOptional(userRepository.findById(userId));
    }

    public void removeUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
