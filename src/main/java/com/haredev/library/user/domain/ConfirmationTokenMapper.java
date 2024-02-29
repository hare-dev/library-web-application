package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.ConfirmationTokenResponse;

class ConfirmationTokenMapper {

    ConfirmationTokenResponse toConfirmationTokenResponse(final VerificationToken verificationToken) {
        return ConfirmationTokenResponse.builder()
                .token(verificationToken.getToken())
                .build();
    }
}
