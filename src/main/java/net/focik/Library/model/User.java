package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int idUser;
    private String login;
    private String password;
    private String name;
    boolean isAdmin;
}
