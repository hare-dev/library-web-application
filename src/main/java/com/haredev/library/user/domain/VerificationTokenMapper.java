package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.ConfirmationTokenResponse;

class VerificationTokenMapper {

    ConfirmationTokenResponse toConfirmationTokenResponse(final VerificationToken verificationToken) {
        return ConfirmationTokenResponse.builder()
                .token(verificationToken.getToken())
                .build();
    }
}
