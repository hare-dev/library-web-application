package com.haredev.library.book.samples

import com.haredev.library.book.domain.dto.CommentCreateDto
import com.haredev.library.book.domain.dto.update.CommentUpdateDto
import groovy.transform.CompileStatic
import pl.amazingcode.timeflow.Time

@CompileStatic
final class SampleComments {
    public static final Long notExistCommentWithThisId = 5000L

    static CommentCreateDto createCommentSample(Long commentId, String description) {
        final var createdAt = Time.instance().now()
        return CommentCreateDto.builder()
                .commentId(commentId)
                .description(description)
                .createdAt(createdAt)
                .build()
    }

    static CommentUpdateDto createCommentWithDataToUpdateSample() {
        final var now = Time.instance().now()
        return CommentUpdateDto.builder()
                .description("UPDATE_DESCRIPTION")
                .updatedAt(now)
                .build()
    }
}
