package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.book.domain.dto.update.CommentUpdateDto;
import com.haredev.library.infrastructure.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    Comment toUpdate(final CommentUpdateDto toUpdate) {
        return Comment.builder()
                .id(id)
                .bookId(bookId)
                .description(toUpdate.description())
                .createdAt(createdAt)
                .updatedAt(toUpdate.updatedAt())
                .build();
    }
}
