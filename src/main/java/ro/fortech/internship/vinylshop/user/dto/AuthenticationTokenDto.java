package ro.fortech.internship.vinylshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.fortech.internship.vinylshop.role.model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationTokenDto {

    @NotNull
    private UUID id;
    @NotBlank
    private String token;
    @NotNull
    private Role role;
}
