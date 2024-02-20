package com.haredev.library.book.samples

import com.haredev.library.book.domain.dto.CommentCreateDto
import com.haredev.library.book.domain.dto.CommentUpdateDto
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
final class SampleComments {
    public static final Long notExistCommentWithThisId = 5000L

    static CommentCreateDto createCommentSample(Long commentId, String description, LocalDateTime createdTime) {
        return CommentCreateDto.builder()
                .commentId(commentId)
                .description(description)
                .creationDate(createdTime)
                .build()
    }

    static CommentUpdateDto createCommentWithDataToUpdateSample() {
        return CommentUpdateDto.builder()
                .description("UPDATE_DESCRIPTION")
                .updateTime(LocalDateTime.now())
                .build()
    }
}
