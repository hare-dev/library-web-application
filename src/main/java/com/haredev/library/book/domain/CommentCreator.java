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
                .fk_id(request.getBookId())
                .description(request.getDescription())
                .createdTime(request.getCreatedTime())
                .build();
    }
}
