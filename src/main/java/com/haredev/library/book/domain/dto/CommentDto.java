package com.haredev.library.book.domain.dto;

import java.time.LocalDateTime;

public record CommentDto(
        String description,
        LocalDateTime createdTime
) { }
