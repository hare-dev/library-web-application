package com.haredev.library.book.domain;

import com.haredev.library.book.dto.CategoryCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long categoryId;
    private String name;
    private String description;
    @OneToMany
    @JoinColumn(name = "bookId")
    private List<Book> books;

    void addBook(Book book) {
        books.add(book);
    }

    CategoryCreateDto response() {
        return CategoryCreateDto.builder()
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .build();
    }
}
