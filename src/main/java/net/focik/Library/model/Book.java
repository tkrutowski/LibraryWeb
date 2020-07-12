package net.focik.Library.model;

import java.time.LocalDate;
import java.util.List;


public class Book {
    private int idBook;
    private int idUser;
    private int idBookStore;
    private int idSeries;
    private List<Integer> authorList;
    private List<Integer> categoryList;
    private String title;
    private String subTitle;
    private String description;
    private String cover;
    private EdytionType edytionType;
    private ReadingStatus readingStatus;
    private OwnershipStatus ownershipStatus;
    private LocalDate readFrom;
    private LocalDate readTo;
    private String info;
    private boolean isRead;

}
