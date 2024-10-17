package com.example.curd_with_jwt.entity;

import com.example.curd_with_jwt.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    // Custom constructor to create MyUser from MyUserRequest
    public MyUser(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
