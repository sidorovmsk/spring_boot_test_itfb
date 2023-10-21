package spring_boot_java.test_itfb.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_boot_java.test_itfb.models.Author;
import spring_boot_java.test_itfb.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findOne(int id) {
        Optional<Author> foundAuthor = authorRepository.findById(id);
        return foundAuthor.orElse(null);
    }

//    public List<Book> findByBookNameStartingWith(String name) {
//        return bookRepository.findByBookNameStartingWith(name);
//    }
//
//    public List<Book> findWithPagination(Integer page, Integer books_per_page, boolean sort_by_year) {
//        if (sort_by_year)
//            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
//        else
//            return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
//    }
//
//    @Transactional
//    public void save(@Valid Book book) {
//        bookRepository.save(book);
//    }
//
//    @Transactional
//    public void update(int id, Book updatedBook) {
//        updatedBook.setId(id);
////        updatedBook.setOwner(bookRepository.findById(id).get().getOwner());
//        bookRepository.save(updatedBook);
//    }
//
//    @Transactional
//    public void delete(int id) {
//        bookRepository.deleteById(id);
//    }


//    public Person getBookOwner(int id) {
//        Optional<Book> book = bookRepository.findById(id);
//        return book.map(Book::getOwner).orElse(null);
//    }

//    @Transactional
//    public void release(int id) {
//        findOne(id).setOwner(null);
//        findOne(id).setExpired(false);
//        findOne(id).setTakenAt(null);
//    }
//
//    @Transactional
//    public void assign(int id, Person selectedPerson) {
//        findOne(id).setOwner(selectedPerson);
//        findOne(id).setTakenAt(new Date());
//    }


}