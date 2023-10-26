package spring_boot_java.test_itfb.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.repositories.BookRepository;
import spring_boot_java.test_itfb.repositories.CustomBookRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public void save(@Valid Book book) {
        bookRepository.save(book);
    }

    public List<Book> findBooksByPartAuthorName(String authorNamePart) {
        return customBookRepository.findBooksByPartAuthorName(authorNamePart);
    }

    @Transactional
    public ResponseEntity<?> delete(int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok("Book with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }

}