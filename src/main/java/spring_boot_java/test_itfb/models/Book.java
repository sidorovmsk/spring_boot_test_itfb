package spring_boot_java.test_itfb.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title should not to be empty")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

}
