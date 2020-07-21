package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Series {
    private int idSeries;
    private String title;
    private String description;

    public Series(int idSeries, String title, String description) {
        this.idSeries = idSeries;
        this.title = title;
        this.description = description;
    }

    public Series() {
    }
}
