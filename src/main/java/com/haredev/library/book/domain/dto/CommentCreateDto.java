package com.haredev.library.book.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentCreateDto(
        Long commentId,
        Long bookId,
        String description,
        LocalDateTime createdTime
) { }
