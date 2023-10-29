package spring_boot_java.test_itfb.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_java.test_itfb.exceptions.BookNotFoundException;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.repositories.BookRepository;
import spring_boot_java.test_itfb.repositories.CustomBookRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class BookService {
    private final BookRepository bookRepository;
    private final CustomBookRepository customBookRepository;

    @Autowired
    public BookService(BookRepository bookRepository, CustomBookRepository customBookRepository) {
        this.bookRepository = bookRepository;
        this.customBookRepository = customBookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    public ResponseEntity<?> save(@Valid Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok("Book with title " + book.getTitle() + " has been created/updated.");
    }

    public List<Book> findBooksByPartAuthorName(String authorNamePart) {
        return customBookRepository.findBooksByPartAuthorName(authorNamePart);
    }

    public ResponseEntity<?> delete(int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok("Book with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }

    public ResponseEntity<?> showBookById(int id) {
        Book book = findOne(id);
        if (book == null) {
            throw new BookNotFoundException("Книга с идентификатором " + id + " не найдена.");
        }
        return ResponseEntity.ok(book);
    }

    public ResponseEntity<?> editBookById(int id, Book updatedBook) {
        Book book = findOne(id);
        if (book == null) {
            throw new BookNotFoundException("Книга с идентификатором " + id + " не найдена.");
        } else {
            book.setTitle(updatedBook.getTitle());
            return save(book);
        }
    }

}