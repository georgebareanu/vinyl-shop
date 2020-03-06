package ro.fortech.internship.vinylshop.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDto {

    @NotBlank(message = "This field can't empty.")
    private String name;

    @Range(min = 0, message = "Please enter a value bigger than 0.")
    private Double price;

    @Range(min = 0, message = "Please enter a value bigger than 0.")
    private Integer stock;
}
