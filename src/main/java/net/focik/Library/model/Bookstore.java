package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bookstore {
    private int idBookStore;
    private String name;
    private String www;

    public Bookstore(int idBookStore, String name, String www) {
        this.idBookStore = idBookStore;
        this.name = name;
        this.www = www;
    }

    public Bookstore() { }
}
