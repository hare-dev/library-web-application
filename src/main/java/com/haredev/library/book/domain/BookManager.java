package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.haredev.library.book.domain.api.error.BookError.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RequiredArgsConstructor
class BookManager {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final BookCreator bookCreator;
    private final CommentCreator commentCreator;

    public Book addBook(BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book);
    }

    public Option<Book> findBookById(Long bookId) {
        return Option.ofOptional(bookRepository.findById(bookId));
    }

    public void removeBookById(Long bookId) { bookRepository.deleteById(bookId); }

    public List<Book> fetchAllBooksWithPageable(int page) {
        final int pageSize = 20;
        return bookRepository.findAll(PageRequest.of(page, pageSize));
    }

    private Comment createComment(CommentCreateDto request) {
        Comment comment = commentCreator.from(request);
        commentRepository.save(comment);
        return comment;
    }

    public Either<BookError, CommentDto> addCommentToBook(CommentCreateDto commentCreateDto) {
        return validateParameters(commentCreateDto)
                .flatMap(this::addComment);
    }

    private Either<BookError, CommentCreateDto> validateParameters(CommentCreateDto commentCreateDto) {
        return API.Match(commentCreateDto)
                .option(
                        Case($(argument -> Objects.isNull(argument.getDescription())), NULL_OR_EMPTY_DESCRIPTION),
                        Case($(argument -> Objects.isNull(argument.getDateAdded())), NULL_DATE_ADDED),
                        Case($(argument -> argument.getDescription().isEmpty()), NULL_OR_EMPTY_DESCRIPTION))
                .toEither(commentCreateDto)
                .swap();
    }

    private Either<BookError, CommentDto> addComment(CommentCreateDto request) {
        return findBookById(request.getBookId())
                .toEither(BOOK_NOT_FOUND)
                .map(book -> {
                    Comment comment = createComment(request);
                    book.addComment(comment);
                    return comment.toDto();
                });
    }

    public Option<Comment> findCommentById(Long commentId) {
        return Option.ofOptional(commentRepository.findById(commentId));
    }

    public List<CommentDto> getBookByIdWithComments(Long bookId) {
        return Option.ofOptional(bookRepository.findById(bookId))
                .map(Book::getAllComments)
                .getOrElse(Collections.emptyList());
    }

    public void removeCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}