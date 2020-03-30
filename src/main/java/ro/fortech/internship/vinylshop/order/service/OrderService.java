package ro.fortech.internship.vinylshop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.common.exception.InvalidQuantityException;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.order.model.Order;
import ro.fortech.internship.vinylshop.order.model.OrderStatus;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;
import ro.fortech.internship.vinylshop.user.service.AuthenticatedUser;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final AuthenticatedUser authenticatedUser;

    public List<Order> getOrders() {
        User user = authenticatedUser.getAuthenticatedUser();
        log.info("User {} requests to see own orders", user.getId());
        return user.getOrders();
    }

    @Transactional
    public void create() {
        User user = authenticatedUser.getAuthenticatedUser();
        log.info("User {} requests to place an order", user.getId());
        Cart cart = user.getCart();

        if (isCartEmpty(user)) {
            throw new InvalidException("Order can't be placed. Please place items in cart first.");
        }

        Order order = Order.builder().dateCreated(LocalDate.now())
                .cost(cart.getTotalCost()).status(OrderStatus.INCOMPLETE).build();

        user.getOrders().add(order);

        if (updateStock(user)) {
            user.getCart().getItemsInCart().stream()
                    .filter(c -> c.getOrderId() == null)
                    .forEach(c -> c.setOrderId(order.getId()));
            cart.setTotalCost(0.0);
            cart.setNumberOfItems(0);
            userRepository.save(user);
        }
    }

    private boolean updateStock(User user) {
        user.getCart().getItemsInCart().stream()
                .filter(c -> c.getOrderId() == null)
                .forEach(c -> {
                    Item item = itemRepository.findByName(c.getItem().getName());
                    if (c.getQuantity() < item.getQuantity()) {
                        item.setQuantity(item.getQuantity() - c.getQuantity());
                    } else {
                        throw new InvalidQuantityException("Insufficient stock");
                    }
                });
        return true;
    }

    private boolean isCartEmpty(User user) {
        List<CartItem> cartItems = user.getCart().getItemsInCart();
        List<CartItem> itemsInCart = cartItems.stream()
                .filter(c -> c.getOrderId() == null)
                .collect(Collectors.toList());
        return itemsInCart.isEmpty();
    }
}
