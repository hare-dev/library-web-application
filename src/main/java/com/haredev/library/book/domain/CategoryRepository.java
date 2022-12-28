package com.haredev.library.book.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface CategoryRepository extends Repository<Category, Long> {
    Category save(Category category);
    Optional<Category> findById(Long categoryId);
    List<Category> findAll(Pageable pageable);
    void deleteById(Long categoryId);
}
