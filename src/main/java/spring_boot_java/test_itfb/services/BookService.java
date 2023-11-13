package spring_boot_java.test_itfb.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_java.test_itfb.exceptions.AuthorNotFoundException;
import spring_boot_java.test_itfb.exceptions.BookNotFoundException;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.repositories.BookRepository;
import spring_boot_java.test_itfb.repositories.CustomBookRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
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

    public Optional<Book> findBookById(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<String> save(@Valid Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok("Book with title " + book.getTitle() + " has been created/updated.");
    }

    public List<Book> findBooksByPartAuthorName(String authorNamePart) {
        return customBookRepository.findBooksByPartAuthorName(authorNamePart);
    }

    @Transactional
    public ResponseEntity<String> delete(int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok("Book with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }

    public ResponseEntity<Book> showBookById(int id) {
        Optional<Book> bookOptional = findBookById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return ResponseEntity.ok(book);
        } else {
            throw new BookNotFoundException("Книга с идентификатором " + id + " не найдена.");
        }
    }

    @Transactional
    public ResponseEntity<String> editBookById(int id, Book updatedBook) {
        Optional<Book> bookOptional = findBookById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(updatedBook.getTitle());
            return ResponseEntity.ok(save(book).getBody());
        } else {
            throw new AuthorNotFoundException("Книга с идентификатором " + id + " не найдена.");
        }
    }

}