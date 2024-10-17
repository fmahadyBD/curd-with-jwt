package com.example.curd_with_jwt.request;

import com.example.curd_with_jwt.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UpdateMyUserRequest {

    private long id;
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Give correct email")
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 8, message = "Password must be at least 8 character long. ")
    private String password;
    private UserRole role;
}
