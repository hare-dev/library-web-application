package com.haredev.library.book.samples

import com.haredev.library.book.domain.dto.CommentCreateDto
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
final class SampleComments {
    static CommentCreateDto createCommentSample(Long commentId, Long bookId, String description, LocalDateTime dateAdded) {
        return CommentCreateDto.builder()
                .commentId(commentId)
                .bookId(bookId)
                .description(description)
                .dateAdded(dateAdded)
                .build()
    }
}
