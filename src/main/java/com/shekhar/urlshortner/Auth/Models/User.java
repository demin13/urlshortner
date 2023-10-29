package com.shekhar.urlshortner.Auth.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role_map",
            joinColumns = @JoinColumn(name = "uid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rid", referencedColumnName = "id")
    )
    private Set<Role> roles;

}
