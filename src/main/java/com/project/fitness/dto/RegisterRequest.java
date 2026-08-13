package com.project.fitness.dto;

import com.project.fitness.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 15, message = "password must be 6-15 characters")
    private String password;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last is required")
    private String lastName;
    private Role role;
}
