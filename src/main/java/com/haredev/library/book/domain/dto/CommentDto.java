package com.haredev.library.book.domain.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record CommentDto(
        String description,
        Instant createdAt,
        Instant updatedAt
) { }
