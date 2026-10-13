package com.adarsh.MedicareAi.dto;


import com.adarsh.MedicareAi.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 20,message = "Name should contain 3-12 characters")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Enter Valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12,message = "Password should contain 8-12 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Role is required")
    private Role role;
}
