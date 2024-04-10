package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentPublicDetailsDto;
import com.haredev.library.book.domain.dto.update.BookUpdateDto;
import com.haredev.library.book.domain.dto.update.CommentUpdateDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.haredev.library.book.domain.api.error.BookError.BOOK_NOT_FOUND;
import static com.haredev.library.book.domain.api.error.BookError.COMMENT_NOT_FOUND;

@RequiredArgsConstructor
public class BookFacade {
    private final BookManager bookManager;
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;

    public Either<BookError, BookCreateDto> addBook(final BookCreateDto request) {
        return bookManager.addBook(request).map(bookMapper::toBookCreateResponse);
    }

    public Either<BookError, BookCreateDto> findBookById(final Long bookId) {
        return bookManager.findBookById(bookId).map(bookMapper::toBookCreateResponse).toEither(BOOK_NOT_FOUND);
    }

    public List<BookCreateDto> fetchAllBooks(final int page) {
        return bookManager.fetchAllBooksWithPageable(page)
                .stream()
                .map(bookMapper::toBookCreateResponse)
                .collect(Collectors.toList());
    }

    public void removeBookById(final Long bookId) {
        bookManager.removeBookById(bookId);
    }

    public Either<BookError, CommentPublicDetailsDto> addCommentToBook(final CommentCreateDto request, final Long bookId) {
        return bookManager.addCommentToBook(request, bookId);
    }

    public Either<BookError, CommentPublicDetailsDto> findCommentById(final Long commentId) {
        return bookManager.findCommentById(commentId).map(commentMapper::toDto).toEither(COMMENT_NOT_FOUND);
    }

    public Either<BookError, List<CommentPublicDetailsDto>> getBookByIdWithComments(final Long bookId) {
        return bookManager.findBookById(bookId)
                .toEither(BOOK_NOT_FOUND)
                .map(book -> book.comments.stream().map(commentMapper::toDto).toList());
    }

    public void removeCommentById(final Long commentId) {
        bookManager.removeCommentById(commentId);
    }

    public Either<BookError, BookCreateDto> updateBookById(final Long bookId, final BookUpdateDto request) {
        return bookManager.updateBookById(bookId, request);
    }

    public Either<BookError, CommentPublicDetailsDto> updateCommentById(final Long commentId, final CommentUpdateDto request) {
        return bookManager.updateCommentById(commentId, request);
    }
}