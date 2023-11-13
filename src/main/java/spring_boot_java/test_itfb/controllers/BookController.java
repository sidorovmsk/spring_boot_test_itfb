package spring_boot_java.test_itfb.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring_boot_java.test_itfb.models.Book;
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

    @GetMapping("/view/books")
    public String getBooksView() {
        log.info("GET request to /view/books");
        return "books/books";
    }

    @ResponseBody
    @GetMapping("/books")
    public List<Book> getBooksJsonList() {
        log.info("GET request to /books");
        return bookService.findAll();
    }

    @ResponseBody
    @GetMapping("/books/{id}")
    public ResponseEntity<?> showBookById(@PathVariable("id") int id) {
        log.info("GET request to /books/" + id);
        return bookService.showBookById(id);
    }

    @GetMapping("/view/books/{id}")
    public String getBookShowView(@PathVariable("id") int id) {
        log.info("GET request to /view/books/" + id);
        return "books/show";
    }

    @GetMapping("/view/books/edit/{id}")
    public String getBookEditView(@PathVariable("id") int id) {
        log.info("GET request to /view/books/edit/" + id);
        return "books/edit";
    }

    @ResponseBody
    @PutMapping("/books/{id}")
    public ResponseEntity<?> editBookById(@PathVariable("id") int id,
                                          @RequestBody Book updatedBook) {
        log.info("PUT request to /books/" + id);
        return bookService.editBookById(id, updatedBook);
    }

    @GetMapping("/view/books/create")
    public String getBookCreateView() {
        log.info("GET request to /view/books/create");
        return "books/create";
    }

    @ResponseBody
    @PostMapping("/books")
    public ResponseEntity<String> createBook(@RequestBody Book newBook) {
        log.info("POST request to /books with title = " + newBook.getTitle());
        return bookService.save(newBook);
    }

    @ResponseBody
    @GetMapping("/findbooks")
    public List<Book> findBooksByPartAuthorName(@RequestParam String sometext) {
        log.info("GET request to /findbooks with text = " + sometext);
        return bookService.findBooksByPartAuthorName(sometext);
    }

    @GetMapping("/view/findbooks")
    public String getBookSearchView() {
        log.info("GET request to /view/findbooks");
        return "books/search";
    }

    @ResponseBody
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") int id) {
        log.info("DELETE request to /books/" + id);
        return bookService.delete(id);
    }

}
