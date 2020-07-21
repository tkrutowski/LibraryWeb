package net.focik.Library.model;

import com.sun.javafx.beans.IDProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "bookstores")
public class BookstoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bookstore")
    private int idBookstore;
    private String name;
    private String www;

    public BookstoreEntity( String name, String www) {
        this.name = name;
        this.www = www;
    }

    public BookstoreEntity() { }
}
