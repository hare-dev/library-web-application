package com.haredev.library.book.domain;

import com.haredev.library.book.dto.CategoryCreateDto;

import static java.util.Objects.requireNonNull;

class CategoryCreator {
    Category from(CategoryCreateDto categoryCreateDto) {
        requireNonNull(categoryCreateDto);
        return Category.builder()
                .categoryId(categoryCreateDto.getCategoryId())
                .name(categoryCreateDto.getName())
                .description(categoryCreateDto.getDescription())
                .build();
    }
}
