package ro.fortech.internship.vinylshop.cart.converter;

import ro.fortech.internship.vinylshop.cart.dto.CartDisplayDto;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.cartitem.converter.CartItemDtoConverter;

import java.util.stream.Collectors;

public class CartDtoConverter {
    public static CartDisplayDto toCartDisplayDtoFromCart(Cart cart) {
        CartDisplayDto cartDisplayDto = new CartDisplayDto();
        cartDisplayDto.setNumberOfItems(cart.getNumberOfItems());
        cartDisplayDto.setTotalCost(cart.getTotalCost());
        cartDisplayDto.setCartItems(cart.getItemsInCart()
                .stream()
                .filter(c -> c.getOrderId() == null)
                .map(CartItemDtoConverter::toCartItemDisplayDtoFromCartItem)
                .collect(Collectors.toList()));
        return cartDisplayDto;
    }
}