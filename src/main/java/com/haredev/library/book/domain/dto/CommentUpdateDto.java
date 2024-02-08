package com.haredev.library.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentUpdateDto {
    private final String description;
    private final LocalDateTime createdTime;
}
