package com.cava.examples.commons.users.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private boolean enabled;
    @ManyToMany
    @JoinTable(
            name = "user_roles", joinColumns =
            @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"user_id","role_id"}
            )
    )
    private List<Role>  roles;



}
