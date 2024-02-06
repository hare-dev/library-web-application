package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.infrastructure.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentId_generator")
    private Long id;
    private Long bookId;
    private String description;
    private LocalDateTime createdTime;

    CommentCreateDto response() {
        return CommentCreateDto.builder()
                .commentId(id)
                .description(description)
                .createdTime(createdTime)
                .build();
    }

    CommentDto toDto() {
        return CommentDto.builder()
                .description(description)
                .createdTime(createdTime)
                .build();
    }
}
