package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
class CommentCreator {
    Comment from(final CommentCreateDto request) {
        requireNonNull(request);
        return Comment.builder()
                .id(request.getCommentId())
                .fk_book_id(request.getFk_book_id())
                .description(request.getDescription())
                .createdTime(request.getCreatedTime())
                .build();
    }
}
