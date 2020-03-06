package ro.fortech.internship.vinylshop.cart.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.cart.repository.CartRepository;
import ro.fortech.internship.vinylshop.cart.service.CartService;
import ro.fortech.internship.vinylshop.cartitem.dto.CartItemAddToCardDto;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;
import ro.fortech.internship.vinylshop.cartitem.repository.CartItemRepository;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.model.ItemStatus;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import java.util.UUID;

@Component
public class CartSetupTest {


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    public void deleteItemsFromDatabase() {
        itemRepository.deleteAll();
    }

    public UUID addItemsInCart(User user) {
        Item item = Item.builder().name("Guns'n'Roses")
                .price(24.3).quantity(1).status(ItemStatus.ACTIVE).build();
        itemRepository.save(item);

        CartItem cartItem = CartItem.builder().item(item).quantity(1).build();
        cartItem = cartItemRepository.save(cartItem);

        user.getCart().getItemsInCart().add(cartItem);
        user.getCart().setNumberOfItems(1);
        user.getCart().setTotalCost(24.3 * 1);

        userRepository.save(user);
        return cartItem.getId();
    }

    public UUID createItem() {
        Item item = Item.builder().name("Guns'n'Roses")
                .price(24.3).quantity(5).status(ItemStatus.ACTIVE).build();
        itemRepository.save(item);
        return item.getId();
    }

    public void addToCartWithVariableQuantity(UUID userId, int quantity) {
        UUID itemId = createItem();
        CartItemAddToCardDto addCartItemDto = new CartItemAddToCardDto(itemId, quantity);
        cartService.addItem(userId, addCartItemDto);
    }

    public void removeCartItemFromCart(UUID userId, UUID cartItemId) {
        cartService.removeItem(userId, cartItemId);
    }
}
