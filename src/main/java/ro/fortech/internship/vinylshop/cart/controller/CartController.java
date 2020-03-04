package ro.fortech.internship.vinylshop.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.cart.dto.CartDisplayDto;
import ro.fortech.internship.vinylshop.cart.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("api/users/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDisplayDto getItems(@RequestHeader UUID userId) {
        return cartService.getItems(userId);
    }

}