package spring_boot_java.test_itfb.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.models.Person;
import spring_boot_java.test_itfb.services.BookService;

import java.util.List;

@Controller
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
        return bookService.findAll();
    }

    @ResponseBody
    @GetMapping("api/book/{id}")
    public ResponseEntity<?> shodw(@PathVariable("id") int id) {
        Book book = bookService.findOne(id);
        if (book == null) { //todo описание ниже 85 урок пересмотреть и в 65 строке тоже самое
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Книга не найдена, реализовать централизованное управление исключениями. При запросе несуществующей книги генерить исключение BookNotFound");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
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
