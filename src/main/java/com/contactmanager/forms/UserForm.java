package com.contactmanager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForm {

    @NotBlank(message = "Username is required...")
    @Size(min=1,max = 25,message = "Min 3 character is required...")
    private String name;

    @Email(message = "Invalid Email...")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 1,message = "Minimum 6 characters is allowed...")
    private String password;

    @Size(min=1,max = 10,message = "Invalid Phone Number...")
    private String phoneNumber;
    private String about;
}
