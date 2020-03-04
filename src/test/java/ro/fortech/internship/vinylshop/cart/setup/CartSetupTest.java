package ro.fortech.internship.vinylshop.cart.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.cart.repository.CartRepository;
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

    public void deleteCartsFromDatabase() {
        cartRepository.deleteAll();
    }

    public void AddItemsInCart(User user) {
        Item item = Item.builder().id(UUID.randomUUID()).name("Guns'n'Roses")
                .price(24.3).quantity(200).status(ItemStatus.ACTIVE).build();
        item = itemRepository.save(item);

        CartItem cartItem = CartItem.builder().item(item).quantity(1).build();
        cartItem = cartItemRepository.save(cartItem);

        user.getCart().getItemsInCart().add(cartItem);
        user.getCart().setNumberOfItems(1);
        user.getCart().setTotalCost(24.3 * 200);

        userRepository.save(user);
    }

}
