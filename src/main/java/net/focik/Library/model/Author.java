package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Author {
    private int id;
    private String firstName;
    private String lastName;

    public Author() {
    }

    public Author(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
