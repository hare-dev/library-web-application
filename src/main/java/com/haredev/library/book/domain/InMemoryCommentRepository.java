package com.haredev.library.book.domain;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryCommentRepository implements CommentRepository {
    private final ConcurrentHashMap<Long, Comment> inMemoryComment = new ConcurrentHashMap<>();

    @Override
    public Comment save(final Comment comment) {
        requireNonNull(comment);
        inMemoryComment.put(comment.response().getCommentId(), comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(final Long commentId) {
        return Optional.ofNullable(inMemoryComment.get(commentId));
    }

    @Override
    public void deleteById(final Long commentId) {
        inMemoryComment.remove(commentId);
    }
}
