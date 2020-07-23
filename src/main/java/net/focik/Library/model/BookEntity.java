package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Column(name = "id_user")
    private int idUser;
    @Column(name = "id_bookstore")
    private int idBookstore;
    @Column(name = "id_series")
    private int idSeries;
    private Set<Integer> authorList;//dodać join
    @Column(name = "id_book")
    private Set<Integer> categoryList;//dodać join
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

}
