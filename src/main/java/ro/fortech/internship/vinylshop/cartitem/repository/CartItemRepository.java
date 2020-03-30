package ro.fortech.internship.vinylshop.cartitem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fortech.internship.vinylshop.cartitem.model.CartItem;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
