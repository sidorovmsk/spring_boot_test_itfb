package spring_boot_java.test_itfb.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.models.Author;
import spring_boot_java.test_itfb.models.Book;
import spring_boot_java.test_itfb.services.AuthorService;

import java.util.List;

@Controller
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAuthors() {
        log.info("GET request to /authors");
        return "authors/authors";
    }

    @ResponseBody
    @GetMapping("api/authors")
    public List<Author> getAllAuthors() {
        log.info("GET request to api/authors");
        return authorService.findAll();
    }

    @ResponseBody
    @GetMapping("api/author/{id}")
    public ResponseEntity<?> show(@PathVariable("id") int id) {
        log.info("GET request to api/authors/" + id);
        Author author = authorService.findOne(id);
        if (author == null) { //todo описание ниже
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Автор не найден, реализовать централизованное управление исключениями.");
        }
        return ResponseEntity.ok(author);
    }

    @GetMapping("/author/{id}")
    public String api_show_rename(@PathVariable("id") int id) {
        log.info("GET request to /authors/" + id);
        return "authors/show";
    }

    @GetMapping("/author_edit/{id}")
    public String showUserById(@PathVariable("id") int id) {
        log.info("GET request to /author_edit/" + id);
        return "authors/edit";
    }

    @ResponseBody
    @PutMapping("/author_edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Author updatedAuthor) {
        log.info("PUT request to /author_edit/" + id);
        Author author = authorService.findOne(id);

        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        } else {
            author.setName(updatedAuthor.getName());
            authorService.save(author);
            return ResponseEntity.ok("Author with ID " + id + " has been updated.");
        }
    }

    @GetMapping("/create/author")
    public String createView() {
        log.info("GET request to /create/author");
        return "authors/create";
    }

    @ResponseBody
    @PostMapping("/create/author")
    public ResponseEntity<?> createApi(@RequestBody Author newAuthor) {
        log.info("POST request to /create/author with name = " + newAuthor.getName());
        authorService.save(newAuthor);
        return ResponseEntity.ok("Book with title " + newAuthor.getName() + " has been created.");
    }

}
