package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
class CommentCreator {
    Comment from(final CommentCreateDto request, final Long bookId) {
        requireNonNull(request);
        return Comment.builder()
                .id(request.id())
                .bookId(bookId)
                .description(request.description())
                .createdAt(request.createdAt())
                .updatedAt(null)
                .build();
    }
}
