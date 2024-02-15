package com.haredev.library.book.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentUpdateDto(
        String description,
        LocalDateTime createdTime
) { }
