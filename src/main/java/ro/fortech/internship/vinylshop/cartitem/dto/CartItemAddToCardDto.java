package ro.fortech.internship.vinylshop.cartitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemAddToCardDto {

    private UUID itemId;

    @Min(value = 1, message = "Invalid number. Must be a positive number.")
    private Integer quantity;
}