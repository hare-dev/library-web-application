package com.haredev.library.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentCreateDto {
    private final Long commentId;
    private final Long fk_book_id;
    private final String description;
    private final LocalDateTime createdTime;
}
