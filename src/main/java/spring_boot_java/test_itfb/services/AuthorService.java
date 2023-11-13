package spring_boot_java.test_itfb.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_java.test_itfb.exceptions.AuthorNotFoundException;
import spring_boot_java.test_itfb.models.Author;
import spring_boot_java.test_itfb.repositories.AuthorRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(int id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<String> save(@Valid Author author) {
        authorRepository.save(author);
        return ResponseEntity.ok("Author with title " + author.getName() + " has been created/updated.");
    }

    @Transactional
    public ResponseEntity<String> delete(int id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.ok("Author with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }
    }

    public ResponseEntity<Author> showAuthorById(int id) {
        Optional<Author> authorOptional = findAuthorById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            return ResponseEntity.ok(author);
        } else {
            throw new AuthorNotFoundException("Автор с идентификатором " + id + " не найден.");
        }
    }

    @Transactional
    public ResponseEntity<String> editAuthorById(int id, Author updatedAuthor) {
        Optional<Author> authorOptional = findAuthorById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setName(updatedAuthor.getName());
            return ResponseEntity.ok(save(author).getBody());
        } else {
            throw new AuthorNotFoundException("Автор с идентификатором " + id + " не найден.");
        }
    }
}