package com.haredev.library.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class CommentCreateDto {
    private final Long commentId;
    private final Long bookId;
    private final String description;
    private final LocalDateTime dateAdded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentCreateDto that)) return false;
        return Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return commentId != null ? commentId.hashCode() : 0;
    }
}
