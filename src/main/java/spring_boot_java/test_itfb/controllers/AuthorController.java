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
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/view/authors")
    public String getAuthorsView() {
        log.info("GET request to /view/authors");
        return "authors/authors";
    }

    @ResponseBody
    @GetMapping("/authors")
    public List<Author> getAuthorsJsonList() {
        log.info("GET request to /authors");
        return authorService.findAll();
    }

    @ResponseBody
    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> showAuthorById(@PathVariable("id") int id) {
        log.info("GET request to /authors/" + id);
        return authorService.showAuthorById(id);
    }

    @GetMapping("/view/authors/{id}")
    public String getAuthorShowView(@PathVariable("id") int id) {
        log.info("GET request to /view/authors/" + id);
        return "authors/show";
    }

    @GetMapping("/view/authors/edit/{id}")
    public String getAuthorEditView(@PathVariable("id") int id) {
        log.info("GET request to /view/authors/edit/" + id);
        return "authors/edit";
    }

    @ResponseBody
    @PutMapping("/authors/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Author updatedAuthor) {
        log.info("PUT request to /authors/" + id);
        return authorService.editAuthorById(id, updatedAuthor);
    }

    @GetMapping("/view/authors/create")
    public String getAuthorCreateView() {
        log.info("GET request to /view/authors/create");
        return "authors/create";
    }

    @ResponseBody
    @PostMapping("/authors")
    public ResponseEntity<String> createAuthor(@RequestBody Author newAuthor) {
        log.info("POST request to /authors with name = " + newAuthor.getName());
        return authorService.save(newAuthor);
    }

    @ResponseBody
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable("id") int id) {
        log.info("DELETE request to /authors/" + id);
        return authorService.delete(id);
    }

}
