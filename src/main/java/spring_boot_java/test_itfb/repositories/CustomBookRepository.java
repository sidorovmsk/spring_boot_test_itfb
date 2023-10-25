package spring_boot_java.test_itfb.repositories;

import org.springframework.stereotype.Repository;
import spring_boot_java.test_itfb.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomBookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Book> findBooksByAuthorNamePart(String authorNamePart) {
        String sql = "SELECT DISTINCT b.* " +
                "FROM books b " +
                "JOIN authors_books ab ON b.id = ab.book_id " +
                "JOIN authors a ON ab.author_id = a.id " +
                "WHERE a.name LIKE :authorNamePart";

        return (List<Book>) entityManager.createNativeQuery(sql, Book.class)
                .setParameter("authorNamePart", "%" + authorNamePart + "%")
                .getResultList();
    }
}
