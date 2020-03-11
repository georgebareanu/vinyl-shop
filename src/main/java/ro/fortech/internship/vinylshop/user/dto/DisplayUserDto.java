package ro.fortech.internship.vinylshop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisplayUserDto {

    private String email;

    private String firstName;

    private String lastName;
}
