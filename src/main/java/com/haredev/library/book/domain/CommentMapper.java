package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentDto;

class CommentMapper {
    CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .description(comment.getDescription())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
