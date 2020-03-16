package ro.fortech.internship.vinylshop.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.common.exception.InvalidQuantityException;
import ro.fortech.internship.vinylshop.common.exception.ResourceNotFoundException;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.order.model.Order;
import ro.fortech.internship.vinylshop.order.model.OrderStatus;
import ro.fortech.internship.vinylshop.user.model.User;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public List<Order> getOrders(UUID userId) {
        log.info("User {} requests to see own orders", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getOrders();
    }

    @Transactional
    public void create(UUID userId) {
        log.info("User {} requests to place an order", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
