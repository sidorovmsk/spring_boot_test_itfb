package spring_boot_java.test_itfb.controllers;


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
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAuthors() {
        return "authors/authors";
    }

    @ResponseBody
    @GetMapping("api/authors")
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @ResponseBody
    @GetMapping("api/author/{id}")
    public ResponseEntity<?> show(@PathVariable("id") int id) {
        Author author = authorService.findOne(id);
        if (author == null) { //todo описание ниже
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Автор не найден, реализовать централизованное управление исключениями.");
        }
        return ResponseEntity.ok(author);
    }

    @GetMapping("/author/{id}")
    public String api_show_rename(@PathVariable("id") int id) {
        return "authors/show";
    }

    @GetMapping("/author_edit/{id}")
    public String showUserById(@PathVariable("id") int id) {
        return "authors/edit";
    }

    @ResponseBody
    @PutMapping("/author_edit/{id}")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id,
                                          @RequestBody Author updatedAuthor) {
        Author author = authorService.findOne(id);

        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        } else {
            author.setName(updatedAuthor.getName());
            authorService.save(author);
            return ResponseEntity.ok("Author with ID " + id + " has been updated.");
        }
    }

}
