package com.haredev.library.book.domain

import com.haredev.library.book.domain.samples.SampleCategories
import com.haredev.library.book.dto.CategoryCreateDto
import io.vavr.control.Option
import spock.lang.Specification

class CategorySpec extends Specification {
    def facade = new BookConfiguration().bookFacade()

    private static final int PAGE_SIZE = 5

    def final bestseller = SampleCategories.createCategorySample(0L, "Bestsellers", "Best books in library")
    def final adventure = SampleCategories.createCategorySample(1L, "Adventure", "Adventure books with a lot of amazing histories")

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