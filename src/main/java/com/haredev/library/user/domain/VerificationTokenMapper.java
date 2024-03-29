package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.VerificationTokenResponse;

class VerificationTokenMapper {
    VerificationTokenResponse verificationTokenResponse(final VerificationToken verificationToken) {
        return VerificationTokenResponse.builder()
                .token(verificationToken.getToken())
                .build();
    }
}
