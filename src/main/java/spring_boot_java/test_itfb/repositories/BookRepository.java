package spring_boot_java.test_itfb.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot_java.test_itfb.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
