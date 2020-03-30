package ro.fortech.internship.vinylshop.cart.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.fortech.internship.vinylshop.cartitem.dto.CartItemDisplayDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class CartDisplayDto {
    private Integer numberOfItems;
    private Double totalCost;
    private List<CartItemDisplayDto> cartItems;
}