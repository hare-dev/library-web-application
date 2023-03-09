package com.haredev.library.book.query;

import java.util.List;

interface BookQueryRepository {
    List<BookQueryDto> findAllByCriminalCategory();
    List<BookQueryDto> findAllBySoftCover();
}
