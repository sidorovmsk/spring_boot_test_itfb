package spring_boot_java.test_itfb.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_java.test_itfb.models.Author;
import spring_boot_java.test_itfb.models.Book;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
