package com.haredev.library.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentDto {
    private final String description;
    private final LocalDateTime dateAdded;
}
