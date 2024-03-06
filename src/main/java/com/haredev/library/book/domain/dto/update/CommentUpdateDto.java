package com.haredev.library.book.domain.dto.update;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Builder
public record CommentUpdateDto(
        @NotNull
        @Length(min = 0, max = 200, message = "Description must be of 0 - 200 characters")
        String description,
        @NotNull(message = "Update time cannot be null")
        Instant updatedAt
) { }
