package ro.fortech.internship.vinylshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "First name is invalid, field can't be empty")
    private String firstName;
    @NotBlank(message = "Last name is invalid, field can't be empty")
    private String lastName;
    @NotBlank
    @Email(message = "Incorrect email format")
    private String email;
    @NotBlank
    private String password;
}
