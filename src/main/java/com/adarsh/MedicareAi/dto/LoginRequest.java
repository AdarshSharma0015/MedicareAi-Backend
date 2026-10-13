package com.adarsh.MedicareAi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email required")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 8,max=12,message = "Enter password between 8-12 characters")
    private String password;
}