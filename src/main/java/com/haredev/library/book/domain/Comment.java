package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.infrastructure.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentId_generator")
    private Long commentId;
    private String description;
    private LocalDateTime dateAdded;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
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
