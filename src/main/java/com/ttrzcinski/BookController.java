package com.ttrzcinski;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
//import io.micronaut.http.annotation.Get;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class BookController {

    // Create
    @Post("/api/book")
    public BookSaved save(@Valid @Body Book book) {
        BookSaved bookSaved = new BookSaved();
        bookSaved.setName(book.getName());
        bookSaved.setIsbn(UUID.randomUUID().toString());
        return bookSaved;
    }

    // Read
    // TODO Change to /api/books/{author}
    //@Get
    //public BookSaved getOne(String bookName) {
    //   return 1 != 2 ? null : new BookSaved();
    //}
}
