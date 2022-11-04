package com.haredev.library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryCreateDto {
    private final Long categoryId;
    private final String name;
    private final String description;
}
