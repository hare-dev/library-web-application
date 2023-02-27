package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentId_generator")
    private Long commentId;
    private String description;
    private LocalDateTime dateAdded;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public CommentCreateDto response() {
        return CommentCreateDto.builder()
                .commentId(commentId)
                .description(description)
                .dateAdded(dateAdded)
                .build();
    }

    public CommentDto toDto() {
        return CommentDto.builder()
                .description(description)
                .dateAdded(dateAdded)
                .build();
    }
}
