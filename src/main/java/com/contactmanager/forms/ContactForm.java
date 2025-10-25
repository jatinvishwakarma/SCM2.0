package com.contactmanager.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactForm {
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Email is Required")
    private String email;
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid Phone Number")   
    private String phoneNumber;
    private String address;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedinLink;
    private MultipartFile profilePic;

}
