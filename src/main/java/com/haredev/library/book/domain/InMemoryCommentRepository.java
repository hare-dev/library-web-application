package com.haredev.library.book.domain;

import java.util.HashMap;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class InMemoryCommentRepository implements CommentRepository {
    private final HashMap<Long, Comment> inMemoryComment = new HashMap<>();

    @Override
    public Comment save(final Comment comment) {
        requireNonNull(comment);
        inMemoryComment.put(comment.toCommentCreateResponse().commentId(), comment);
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
