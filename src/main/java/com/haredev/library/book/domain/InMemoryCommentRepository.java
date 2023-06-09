package com.haredev.library.book.domain;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryCommentRepository implements CommentRepository {
    ConcurrentHashMap<Long, Comment> inMemoryComment = new ConcurrentHashMap<>();

    @Override
    public Comment save(Comment comment) {
        requireNonNull(comment);
        inMemoryComment.put(comment.response().getCommentId(), comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return Optional.ofNullable(inMemoryComment.get(commentId));
    }

    @Override
    public void deleteById(Long commentId) {
        inMemoryComment.remove(commentId);
    }
}
