package net.focik.Library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;
    private String login;
    private String password;
    private String name;
    @Column(name = "is_admin")
    boolean isAdmin;

    public UserEntity() {
    }
}
