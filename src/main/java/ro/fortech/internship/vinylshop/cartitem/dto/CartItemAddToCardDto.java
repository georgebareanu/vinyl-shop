package ro.fortech.internship.vinylshop.cartitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemAddToCardDto {

    @NotBlank
    private UUID itemId;

    @NotBlank
    @Size(min = 1)
    private Integer quantity;
}