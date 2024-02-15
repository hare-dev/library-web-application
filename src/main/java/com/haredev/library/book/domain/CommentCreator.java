package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
class CommentCreator {
    Comment from(final CommentCreateDto request) {
        requireNonNull(request);
        return Comment.builder()
                .id(request.commentId())
                .bookId(request.bookId())
                .description(request.description())
                .createdTime(request.createdTime())
                .build();
    }
}
