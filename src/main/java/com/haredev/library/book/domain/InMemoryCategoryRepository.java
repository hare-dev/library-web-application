package com.haredev.library.book.domain;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryCategoryRepository implements CategoryRepository {
    private final ConcurrentHashMap<Long, Category> inMemory = new ConcurrentHashMap<>();

    @Override
    public Category save(Category category) {
        requireNonNull(category);
        inMemory.put(category.response().getCategoryId(), category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        requireNonNull(categoryId);
        return Optional.ofNullable(inMemory.get(categoryId));
    }

    @Override
    public List<Category> findAll(Pageable pageable) { return new ArrayList<>(inMemory.values()); }

    @Override
    public void deleteById(Long bookId) {

    }
}
