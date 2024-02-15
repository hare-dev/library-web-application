package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.*;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.haredev.library.book.domain.api.error.BookError.BOOK_NOT_FOUND;
import static com.haredev.library.book.domain.api.error.BookError.COMMENT_NOT_FOUND;

@RequiredArgsConstructor
class BookManager {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final BookCreator bookCreator;
    private final CommentCreator commentCreator;
    private final CommentValidation commentValidation;
    private static final int pageSize = 20;

    public Book addBook(final BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book);
    }

    public Option<Book> findBookById(final Long bookId) {
        return Option.ofOptional(bookRepository.findById(bookId));
    }

    public void removeBookById(final Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> fetchAllBooksWithPageable(final int page) {
        return bookRepository.findAll(PageRequest.of(page, pageSize));
    }

    private Comment createComment(final CommentCreateDto request) {
        Comment comment = commentCreator.from(request);
        commentRepository.save(comment);
        return comment;
    }

    public Either<BookError, CommentDto> addCommentToBook(final CommentCreateDto request) {
        return commentValidation.validateParameters(request)
                .flatMap(this::addComment);
    }

    private Either<BookError, CommentDto> addComment(final CommentCreateDto request) {
        return findBookById(request.bookId())
                .toEither(BOOK_NOT_FOUND)
                .map(book -> {
                    Comment comment = createComment(request);
                    book.addComment(comment);
                    return comment.toDto();
                });
    }

    public Option<Comment> findCommentById(final Long commentId) {
        return Option.ofOptional(commentRepository.findById(commentId));
    }

    public void removeCommentById(final Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Either<BookError, BookCreateDto> updateBookById(final Long bookId, final BookUpdateDto request) {
        return findBookById(bookId)
                .toEither(BOOK_NOT_FOUND)
                .map(book -> book.toUpdate(request))
                .map(book -> bookRepository.save(book).toBookCreateResponse());
    }

    public Either<BookError, CommentDto> updateCommentById(final Long commentId, final CommentUpdateDto request) {
        return findCommentById(commentId)
                .toEither(COMMENT_NOT_FOUND)
                .map(comment -> comment.toUpdate(request))
                .map(comment -> commentRepository.save(comment).toDto());
    }
}