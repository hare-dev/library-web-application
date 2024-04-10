package com.haredev.library.book.samples

import com.haredev.library.book.domain.dto.CommentCreateDto
import com.haredev.library.book.domain.dto.update.CommentUpdateDto
import groovy.transform.CompileStatic
import pl.amazingcode.timeflow.Time

@CompileStatic
trait SampleComments {
    CommentCreateDto comment_best_book = createCommentSample(1L, "Best book")
    CommentCreateDto comment_amazing_adventure = createCommentSample(2L, "Amazing adventure")
    final Long commentWithThisIdNotExist = 5000L

    private CommentCreateDto createCommentSample(final Long id, final String description) {
        final var createdAt = Time.instance().now()
        return CommentCreateDto.builder()
                .id(id)
                .description(description)
                .createdAt(createdAt)
                .build()
    }

    static CommentUpdateDto createCommentSampleWitDataToUpdate() {
        final var now = Time.instance().now()
        return CommentUpdateDto.builder()
                .description("updated_description")
                .updatedAt(now)
                .build()
    }
}