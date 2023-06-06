package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;

import static java.util.Objects.requireNonNull;

class CommentCreator {
    Comment from(CommentCreateDto request, Book book) {
        requireNonNull(request);
        return Comment.builder()
                .commentId(request.getCommentId())
                .description(request.getDescription())
                .dateAdded(request.getDateAdded())
                .book(book)
                .build();
    }
}