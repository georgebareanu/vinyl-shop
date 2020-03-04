package ro.fortech.internship.vinylshop.cartitem.converter;

import ro.fortech.internship.vinylshop.cartitem.dto.CartItemDisplayDto;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;

public class CartItemDtoConverter {
    public static CartItemDisplayDto toCartItemDisplayDtoFromCartItem(CartItem cartItem) {
        CartItemDisplayDto cartItemDisplayDto = new CartItemDisplayDto();
        cartItemDisplayDto.setId(cartItem.getId());
        cartItemDisplayDto.setName(cartItem.getItem().getName());
        cartItemDisplayDto.setQuantity(cartItem.getQuantity());
        cartItemDisplayDto.setCost(cartItem.getItem().getPrice());
        return cartItemDisplayDto;
    }
}
