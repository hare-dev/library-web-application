package com.haredev.library.book.domain

import com.haredev.library.book.dto.CategoryCreateDto
import groovy.transform.CompileStatic

@CompileStatic
final class SampleCategories {
    static CategoryCreateDto createCategorySample(Long categoryId, String name, String description) {
        return CategoryCreateDto.builder()
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .build()
    }
}
