package ro.fortech.internship.vinylshop.cart.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.cart.converter.CartDtoConverter;
import ro.fortech.internship.vinylshop.cart.dto.CartDisplayDto;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.cart.repository.CartRepository;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import java.util.UUID;

@Slf4j
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public CartDisplayDto getItems(UUID userId) {
        log.info("User {} requests to see carts details", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findById(user.getCart().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return CartDtoConverter.toCartDisplayDtoFromCart(cart);
    }

}
