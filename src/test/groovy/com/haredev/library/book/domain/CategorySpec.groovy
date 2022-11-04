package com.haredev.library.book.domain

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
}