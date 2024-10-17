package com.example.curd_with_jwt.dto;

import com.example.curd_with_jwt.enums.UserRole;
import lombok.Data;

@Data
public class MyUserDto {
    private Long id;
    private String email;
    private UserRole userRole;

    public MyUserDto(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }


}
