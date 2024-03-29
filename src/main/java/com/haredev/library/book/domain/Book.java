package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.BookCategory;
import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.domain.api.BookStatus;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.update.BookUpdateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.infrastructure.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer yearPublication;
    private Integer pageNumber;
    private String language;
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;
    @Enumerated(EnumType.STRING)
    private BookCover bookCover;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    private String description;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "bookId")
    @Builder.Default
    Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    Book toUpdate(final BookUpdateDto data) {
        return Book.builder()
                .id(this.id)
                .title(data.title())
                .author(data.author())
                .isbn(data.isbn())
                .publisher(data.publisher())
                .yearPublication(data.yearPublication())
                .pageNumber(data.pageNumber())
                .language(data.language())
                .bookCategory(data.bookCategory())
                .bookCover(data.bookCover())
                .bookStatus(this.bookStatus)
                .description(data.description())
                .comments(this.comments)
                .build();
    }
}
