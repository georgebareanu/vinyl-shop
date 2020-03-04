package ro.fortech.internship.vinylshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class DeleteUserDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}