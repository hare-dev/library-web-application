package com.haredev.library.book.domain

import com.haredev.library.book.domain.samples.SampleBooks
import com.haredev.library.book.domain.samples.SampleCategories
import com.haredev.library.book.dto.BookCreateDto
import com.haredev.library.book.dto.CategoryCreateDto
import io.vavr.control.Option
import spock.lang.Specification

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade()

    private static final int PAGE_SIZE = 10

    def final twilight = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final django = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def final bestseller = SampleCategories.createCategorySample(0L, "Bestsellers", "Best books in library")
    def final adventure = SampleCategories.createCategorySample(1L, "Adventure", "Adventure books with a lot of amazing histories")

    def "System should has no one book"() {
        expect:
        facade.fetchAllBooks(PAGE_SIZE).isEmpty()
    }

    def "System has one book"() {
        given: "Should add book to system"
        facade.addBook(twilight)

        when: "System should have one book"
        Option<BookCreateDto> result = facade.findBookById(twilight.bookId)

        then:
        result.get() == twilight
    }

    def "System has two books"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "System should return two books"
        Option<BookCreateDto> twilight_result = facade.findBookById(twilight.bookId)
        Option<BookCreateDto> django_result = facade.findBookById(django.bookId)

        then:
        twilight_result.get() == twilight
        django_result.get() == django
    }

    def "Should return all films from system"() {
        given: "System should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "We ask system to get books"
        List<BookCreateDto> foundBooks = facade.fetchAllBooks(PAGE_SIZE)

        then: "System should return books we have added"
        foundBooks.contains(twilight)
        foundBooks.contains(django)
    }

    def "Should remove book from system"() {
        given: "Should add one book to system"
        facade.addBook(twilight)

        when: "Should remove one book from system"
        facade.removeBookById(twilight.bookId)

        then: "System should be empty"
        facade.fetchAllBooks(PAGE_SIZE).isEmpty()
    }

    def "Should remove two books from system"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should remove two books from system"
        facade.removeBookById(django.bookId)
        facade.removeBookById(twilight.bookId)

        then: "System should be empty"
        facade.fetchAllBooks(PAGE_SIZE).isEmpty()
    }

    def "System should has no one category"() {
        expect:
        facade.fetchAllCategories(PAGE_SIZE).isEmpty()
    }

    def "System has one category"() {
        given: "Should add category to system"
        facade.addCategory(bestseller)

        when: "System should have one category"
        Option<CategoryCreateDto> result = facade.findCategoryById(bestseller.categoryId)

        then:
        result.get() == bestseller
    }

    def "System has two categories"() {
        given: "Should add category to system"
        facade.addCategory(bestseller)
        facade.addCategory(adventure)

        when: "System should have two categories"
        Option<CategoryCreateDto> bestseller_result = facade.findCategoryById(bestseller.categoryId)
        Option<CategoryCreateDto> adventure_result = facade.findCategoryById(adventure.categoryId)

        then:
        bestseller_result.get() == bestseller
        adventure_result.get() == adventure
    }

    def "Should return all categories from system"() {
        given: "System should add two categories"
        facade.addCategory(bestseller)
        facade.addCategory(adventure)

        when: "We ask system to get categories"
        List<CategoryCreateDto> foundCategories = facade.fetchAllCategories(PAGE_SIZE)

        then: "System should return categories we have added"
        foundCategories.contains(bestseller)
        foundCategories.contains(adventure)
    }

    def "Should remove category from system"() {
        given: "Should add one category to system"
        facade.addCategory(bestseller)

        when: "Should remove one category from system"
        facade.removeCategoryById(bestseller.categoryId)

        then: "System should be empty"
        facade.fetchAllCategories(PAGE_SIZE).isEmpty()
    }

    def "Should remove two categories from system"() {
        given: "Should add two categories to system"
        facade.addCategory(bestseller)
        facade.addCategory(adventure)

        when: "Should remove two books from system"
        facade.removeCategoryById(bestseller.categoryId)
        facade.removeCategoryById(adventure.categoryId)

        then: "System should be empty"
        facade.fetchAllCategories(PAGE_SIZE).isEmpty()
    }
}
