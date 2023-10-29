package spring_boot_java.test_itfb.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.models.Author;
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
    public String getAuthorsView() {
        log.info("GET request to /authors");
        return "authors/authors";
    }

    @ResponseBody
    @GetMapping("api/authors")
    public List<Author> getAuthorsJsonList() {
        log.info("GET request to api/authors");
        return authorService.findAll();
    }

    @ResponseBody
    @GetMapping("api/author/{id}")
    public ResponseEntity<?> showAuthorById(@PathVariable("id") int id) {
        log.info("GET request to api/authors/" + id);
        return authorService.showAuthorById(id);
    }

    @GetMapping("/author/{id}")
    public String getAuthorShowView(@PathVariable("id") int id) {
        log.info("GET request to /authors/" + id);
        return "authors/show";
    }

    @GetMapping("/author_edit/{id}")
    public String getAuthorEditView(@PathVariable("id") int id) {
        log.info("GET request to /author_edit/" + id);
        return "authors/edit";
    }

    @ResponseBody
    @PutMapping("/author_edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Author updatedAuthor) {
        log.info("PUT request to /author_edit/" + id);
        return authorService.editUserById(id, updatedAuthor);
    }

    @GetMapping("/create/author")
    public String getAuthorCreateView() {
        log.info("GET request to /create/author");
        return "authors/create";
    }

    @ResponseBody
    @PostMapping("/create/author")
    public ResponseEntity<?> createAuthor(@RequestBody Author newAuthor) {
        log.info("POST request to /create/author with name = " + newAuthor.getName());
        return authorService.save(newAuthor);
    }

    @ResponseBody
    @DeleteMapping("/author_delete/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("id") int id) {
        log.info("DELETE request to /author_delete/" + id);
        return authorService.delete(id);
    }

}
