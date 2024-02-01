package com.haredev.library.book.samples

import com.haredev.library.book.domain.dto.CommentCreateDto
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
final class SampleComments {
    private static final Long bookIdForComment = 0L
    private static final Long commentId = 0L
    private static final Long notExistBookId = 1000L

    static CommentCreateDto createCommentSample(Long commentId, Long bookId,
                                                String description, LocalDateTime createdTime) {
        return CommentCreateDto.builder()
                .commentId(commentId)
                .bookId(bookId)
                .description(description)
                .createdTime(createdTime)
                .build()
    }

    static CommentCreateDto createCommentSampleWithNotExistsBookId() {
        return CommentCreateDto.builder()
        .commentId(0L)
        .bookId(notExistBookId)
        .description("Example description")
        .createdTime(LocalDateTime.now())
        .build()
    }
    
    static CommentCreateDto createCommentSampleWithNullDescription() {
        return CommentCreateDto.builder()
        .commentId(0L)
        .bookId(bookIdForComment)
        .description(null)
        .createdTime(LocalDateTime.now())
        .build()
    }

    static CommentCreateDto createCommentSampleWithEmptyDescription() {
        return CommentCreateDto.builder()
                .commentId(0L)
                .bookId(bookIdForComment)
                .description("")
                .createdTime(LocalDateTime.now())
                .build()
    }

    static CommentCreateDto createCommentSampleWithNullDateAdded() {
        return CommentCreateDto.builder()
        .commentId(commentId)
        .bookId(bookIdForComment)
        .description("Example description")
        .createdTime(null)
        .build()
    }
}
