package spring_boot_java.test_itfb.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring_boot_java.test_itfb.services.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

//    @GetMapping()
//    public String index(Model model,
//                        @RequestParam(value = "page", required = false) Integer page,
//                        @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
//                        @RequestParam(value = "sort_by_year", required = false) boolean sort_by_year) {
//        if (page == null || books_per_page == null)
//            model.addAttribute("books", bookService.findAll());
//        else
//            model.addAttribute("books", bookService.findWithPagination(page, books_per_page, sort_by_year));
//        return "books/index";
//    }
//
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") User user) {
//        model.addAttribute("book", bookService.findOne(id));
//
//        // TODO: 10.03.2023 закончил на этом методе, он работает, сделать методы назначения книги и освобождения

//        if (bookService.getBookOwner(id) != null)
//            model.addAttribute("owner", bookService.getBookOwner(id));
//        else
//            model.addAttribute("people", peopleService.findAll());

//        return "books/show";
//    }
//
//    @GetMapping("/new")
//    public String newBook(@ModelAttribute("book") Book book) {
//        return "books/new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("book") @Valid Book book,
//                         BindingResult bindingResult) {
////        personValidator.validate(person, bindingResult);
//
//        if (bindingResult.hasErrors())
//            return "books/new";
//
//        bookService.save(book);
//        return "redirect:/books";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("book", bookService.findOne(id));
//        return "books/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("book") @Valid Book book,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id) {
////        personValidator.validate(person, bindingResult);//проверка дубликата
//
//        if (bindingResult.hasErrors())
//            return "books/edit";
//
////        bookService.update(id, book);
//        return "redirect:/books";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
////        bookService.delete(id);
//        return "redirect:/books";
//    }
//
//    @PatchMapping("/{id}/release")
//    public String release(@PathVariable("id") int id) {
////        bookService.release(id);
//        return "redirect:/books/" + id;
//    }
//
//    @PatchMapping("/{id}/assign")
//    public String assign(@PathVariable("id") int id, @ModelAttribute("person") User selectedUser) {
////        bookService.assign(id, selectedPerson);
//        return "redirect:/books/" + id;
//    }
//
//    @GetMapping("/search")
//    public String searchPage() {
//        return "books/search";
//    }
//
//    @PostMapping("/search")
//    public String searchByBookNameStartingWith(Model model, @RequestParam(value = "query") String query) {
//        model.addAttribute("books", bookService.findByBookNameStartingWith(query));
//        return "books/search";
//    }

}
