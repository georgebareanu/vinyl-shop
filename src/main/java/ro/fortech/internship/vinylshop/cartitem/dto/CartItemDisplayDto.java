package ro.fortech.internship.vinylshop.cartitem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemDisplayDto {
    private UUID id;
    private String name;
    private Integer quantity;
    private Double cost;
}
