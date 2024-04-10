package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentPublicDetailsDto;

class CommentMapper {
    CommentPublicDetailsDto toDto(Comment comment) {
        return CommentPublicDetailsDto.builder()
                .description(comment.getDescription())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
