package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.book.domain.dto.update.BookUpdateDto;
import com.haredev.library.book.domain.dto.update.CommentUpdateDto;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.haredev.library.book.domain.api.error.BookError.*;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
class BookManager {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final BookCreator bookCreator;
    private final CommentCreator commentCreator;
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;
    private static final int pageSize = 20;

    public Either<BookError, Book> addBook(final BookCreateDto request) {
        if (bookWithDuplicatedIsbnNotExist(request.isbn())) {
            return right(insert(request));
        } else return left(ISBN_DUPLICATED);
    }

    private Book insert(final BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book);
    }

    private boolean bookWithDuplicatedIsbnNotExist(String isbn) {
        return bookRepository.findAll()
                .stream()
                .noneMatch(book -> book.getIsbn().equals(isbn));
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

    private Comment createComment(final CommentCreateDto request, final Long bookId) {
        Comment comment = commentCreator.from(request, bookId);
        commentRepository.save(comment);
        return comment;
    }

    public Either<BookError, CommentDto> addCommentToBook(final CommentCreateDto request, final Long bookId) {
        return findBookById(bookId)
                .toEither(BOOK_NOT_FOUND)
                .map(book -> {
                    Comment comment = createComment(request, bookId);
                    book.addComment(comment);
                    return commentMapper.toDto(comment);
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
                .map(bookRepository::save)
                .map(bookMapper::toBookCreateResponse);
    }

    public Either<BookError, CommentDto> updateCommentById(final Long commentId, final CommentUpdateDto request) {
        return findCommentById(commentId)
                .toEither(COMMENT_NOT_FOUND)
                .map(comment -> comment.toUpdate(request))
                .map(commentRepository::save)
                .map(commentMapper::toDto);
    }
}