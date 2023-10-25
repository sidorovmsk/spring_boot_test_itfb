package spring_boot_java.test_itfb.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.exceptions.BookNotFoundException;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.services.BookService;

import java.util.List;

@Controller
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks() {
        return "books/books";
    }

    @ResponseBody
    @GetMapping("api/books")
    public List<Book> getAllBooks() {
        log.info("Received a request to get the list of books.");
        return bookService.findAll();
    }

    @ResponseBody
    @GetMapping("api/book/{id}")
    public ResponseEntity<?> shodw(@PathVariable("id") int id) {
        Book book = bookService.findOne(id);
        if (book == null) {
            throw new BookNotFoundException("Книга с идентификатором " + id + " не найдена.");
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/book/{id}")
    public String show(@PathVariable("id") int id) {
        return "books/show";
    }

    @GetMapping("/book_edit/{id}")
    public String showUserById(@PathVariable("id") int id) {
        return "books/edit";
    }

    @ResponseBody
    @PutMapping("/book_edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Book updatedBook) {
        Book book = bookService.findOne(id);

        if (book == null) {
            throw new BookNotFoundException("Книга с идентификатором " + id + " не найдена.");
        } else {
            book.setTitle(updatedBook.getTitle());
            bookService.save(book);
            return ResponseEntity.ok("Book with ID " + id + " has been updated.");
        }
    }

    @GetMapping("/create/book")
    public String createView() {
        return "books/create";
    }

    @ResponseBody
    @PostMapping("/create/book")
    public ResponseEntity<?> createApi(@RequestBody Book newBook) {
        bookService.save(newBook);
        return ResponseEntity.ok("Book with title " + newBook.getTitle() + " has been created.");
    }

    @ResponseBody
    @GetMapping("api/findbooks")
    public List<Book> find(@RequestParam String sometext) {
        return bookService.findBooksByAuthorNamePart(sometext);
    }

    @GetMapping("/findbooks")
    public String find() {
        return "books/search";
    }

}
