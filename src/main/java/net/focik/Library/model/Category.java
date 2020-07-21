package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Category {
    private int idCategory;
    private String name;

    public Category() {
    }

    public Category(int idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }
}
