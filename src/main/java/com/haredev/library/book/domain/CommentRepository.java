package com.haredev.library.book.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface CommentRepository extends Repository<Comment, Long> {
    Comment save(Comment comment);
    Optional<Comment> findById(Long commentId);
}
