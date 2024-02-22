package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.haredev.library.user.domain.api.UserError.DUPLICATED_USERNAME;
import static com.haredev.library.user.domain.api.UserError.USER_NOT_FOUND;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
class UserManager {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private static final int pageSize = 20;

    public Option<UserApplication> getUserByUsername(final String username) {
        return Option.ofOptional(userRepository.findByUsername(username));
    }

     public Either<UserError, UserDetailsDto> promoteToAdmin(final Long userId) {
        return findById(userId)
                .toEither(USER_NOT_FOUND)
                .map(user -> {
                    user.promoteToAdmin();
                    return userRepository.save(user).toUserDetails();
                });
    }

    public Either<UserError, RegistrationResponse> registerUser(final RegistrationRequest userRequest) {
        if (userWithDuplicatedUsernameNotExist(userRequest.username())) {
            return right(createUser(userRequest));
        }
        return left(DUPLICATED_USERNAME);
    }

    public Either<UserError, RegistrationResponse> registerAdmin(final RegistrationRequest userRequest) {
        if (userWithDuplicatedUsernameNotExist(userRequest.username())) {
            return right(createAdmin(userRequest));
        }
        return left(DUPLICATED_USERNAME);
    }

    private Boolean userWithDuplicatedUsernameNotExist(final String username) {
        return userRepository.findAll()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    public RegistrationResponse createUser(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildUser(request);
        return userRepository.save(userApplication).toRegistrationResponse();
    }

    public RegistrationResponse createAdmin(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildAdmin(request);
        return userRepository.save(userApplication).toRegistrationResponse();
    }

    public List<UserApplication> fetchAllUsersWithPageable(final int page) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Option<UserApplication> findById(final Long userId) {
        return Option.ofOptional(userRepository.findById(userId));
    }

    public void removeUserById(final Long userId) {
        userRepository.deleteById(userId);
    }
}
