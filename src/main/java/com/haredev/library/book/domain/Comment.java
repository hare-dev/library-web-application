package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
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
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long commentId;
    private Long bookId;
    private String description;
    private LocalDateTime dateAdded;

    public CommentCreateDto response() {
        return CommentCreateDto.builder()
                .commentId(commentId)
                .bookId(bookId)
                .description(description)
                .dateAdded(dateAdded)
                .build();
    }
}
