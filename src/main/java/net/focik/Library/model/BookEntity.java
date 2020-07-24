package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private int idBook;
    //private int idUser;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_bookstore")
    //private int idBookstore;
    BookstoreEntity bookstore;

    @ManyToOne
    @JoinColumn(name = "id_series")
    //private int idSeries;
    private SeriesEntity series;

    @ManyToMany()
    @JoinTable(name = "books_authors",
            joinColumns = {@JoinColumn(name ="id_book")},
            inverseJoinColumns = {@JoinColumn(name ="id_author")})
    private Set<AuthorEntity> authors = new HashSet<>();

    @ManyToMany()//cascade = {CascadeType.ALL})
    @JoinTable(name = "books_categories",
            joinColumns = {@JoinColumn(name ="id_book")},
            inverseJoinColumns = {@JoinColumn(name ="id_category")})
   private Set<CategoryEntity> categories = new HashSet<>();

    private String title;
    private String subtitle;
    private String description;
    private String cover;
    @Column(name = "edition_type")
    private EditionType editionType;
   @Column(name = "reading_status")
    private ReadingStatus readingStatus;
    @Column(name = "ownership_status")
    private OwnershipStatus ownershipStatus;
    @Column(name = "read_from")
    private LocalDate readFrom;
    @Column(name = "read_to")
    private LocalDate readTo;
    private String info;
    @Column(name = "is_read")
    private boolean isRead;

    public void addAuthor(AuthorEntity author){
        authors.add(author);
    }

    public void addCategory(CategoryEntity category){
        categories.add(category);
    }
}
