package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
public class SeriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_series")
    private int idSeries;
    private String title;
    private String description;

    public SeriesEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public SeriesEntity() {
    }
}
