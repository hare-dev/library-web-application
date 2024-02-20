package com.haredev.library.book.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Builder
public record CommentCreateDto(
        Long commentId,
        @NotNull
        @Length(min = 0, max = 200, message = "Description must be of 0 - 200 characters")
        String description,
        @NotNull(message = "Created time cannot be null")
        LocalDateTime creationDate
) { }
