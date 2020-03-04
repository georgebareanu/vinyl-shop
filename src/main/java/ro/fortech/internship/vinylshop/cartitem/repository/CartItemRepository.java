package ro.fortech.internship.vinylshop.cartitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
