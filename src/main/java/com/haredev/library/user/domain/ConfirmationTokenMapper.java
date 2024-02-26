package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.ConfirmationTokenResponse;

class ConfirmationTokenMapper {

    ConfirmationTokenResponse toConfirmationTokenResponse(final ConfirmationToken confirmationToken) {
        return ConfirmationTokenResponse.builder()
                .token(confirmationToken.getToken())
                .build();
    }
}
