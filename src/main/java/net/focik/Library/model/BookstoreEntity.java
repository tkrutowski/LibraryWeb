package net.focik.Library.model;

import com.sun.javafx.beans.IDProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "bookstores")
public class BookstoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bookstore")
    private int idBookstore;
    private String name;
    private String www;
//
//    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY)
//    private Set<BookEntity> books = new HashSet<>();

    public BookstoreEntity( String name, String www) {
        this.name = name;
        this.www = www;
    }

    public BookstoreEntity() { }
}
