package ro.fortech.internship.vinylshop.cart.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.cart.converter.CartDtoConverter;
import ro.fortech.internship.vinylshop.cart.dto.CartDisplayDto;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.cart.repository.CartRepository;
import ro.fortech.internship.vinylshop.cartitem.dto.CartItemAddToCardDto;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;
import ro.fortech.internship.vinylshop.cartitem.repository.CartItemRepository;
import ro.fortech.internship.vinylshop.common.exception.InvalidQuantityException;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository,
                       ItemRepository itemRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public CartDisplayDto getItems(UUID userId) {
        log.info("User {} requests to see carts details", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findById(user.getCart().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return CartDtoConverter.toCartDisplayDtoFromCart(cart);
    }

    public void addItem(UUID userId, CartItemAddToCardDto cartItemAddToCardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Item item = itemRepository.findById(cartItemAddToCardDto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("The requested item does not exist!"));

        CartItem cartItemInCart = user.getCart().getItemsInCart().stream()
                .filter(c -> c.getItem().getName().equals(item.getName()))
                .findAny()
                .orElse(null);

        if (cartItemInCart == null) {
            addItemToCart(user, cartItemAddToCardDto, item);
        } else {
            addItemToCartIfCartItemAlreadyExist(user, cartItemAddToCardDto, item, cartItemInCart);
        }
        log.info("Item {} was successfully inserted into the cart for user with id {}", item.getName(), userId);
    }

    private void addItemToCartIfCartItemAlreadyExist(User user, CartItemAddToCardDto cartItemAddToCardDto,
                                                     Item item, CartItem cartItem) {
        Cart cart = user.getCart();
        int updatedQuantity = cartItem.getQuantity() + cartItemAddToCardDto.getQuantity();

        if (updatedQuantity > item.getQuantity()) {
            throw new InvalidQuantityException("Insufficient stock");
        }

        cartItem.setQuantity(updatedQuantity);
        cart.setNumberOfItems(setQuantity(cart));
        cart.setTotalCost(setTotalCost(cart));
        cartRepository.save(cart);
    }

    private void addItemToCart(User user, CartItemAddToCardDto cartItemAddToCardDto, Item item) {
        CartItem cartItem = new CartItem();
        Cart cart = user.getCart();

        if (cartItemAddToCardDto.getQuantity() > item.getQuantity()) {
            throw new InvalidQuantityException("Insufficient stock");
        }

        cartItem.setQuantity(cartItemAddToCardDto.getQuantity());
        cartItem.setItem(item);

        cart.getItemsInCart().add(cartItem);
        cart.setNumberOfItems(setQuantity(cart));
        cart.setTotalCost(setTotalCost(cart));
        cartRepository.save(cart);
    }

    private int setQuantity(Cart cart) {
        return cart.getItemsInCart()
                .stream()
                .filter(c -> c.getOrderId() == null)
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    private double setTotalCost(Cart cart) {
        return cart.getItemsInCart()
                .stream()
                .filter(c -> c.getOrderId() == null)
                .mapToDouble(c -> c.getItem().getPrice() * c.getQuantity())
                .sum();
    }

    @Transactional
    public void removeItem(UUID userId, UUID cartItemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findById(user.getCart().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        cart.setTotalCost(cart.getTotalCost() - cartItem.getItem().getPrice());
        cart.setNumberOfItems(cart.getNumberOfItems() - cartItem.getQuantity());
        cart.getItemsInCart().remove(cartItem);
        cartRepository.save(cart);
        cartItemRepository.delete(cartItem);
    }

}
