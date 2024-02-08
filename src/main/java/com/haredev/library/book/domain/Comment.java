package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.book.domain.dto.CommentUpdateDto;
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
    private Long fk_book_id;
    private String description;
    private LocalDateTime createdTime;

    Comment update(final CommentUpdateDto toUpdate) {
        return Comment.builder()
                .id(this.id)
                .fk_book_id(this.fk_book_id)
                .description(toUpdate.getDescription())
                .createdTime(toUpdate.getCreatedTime())
                .build();
    }

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
