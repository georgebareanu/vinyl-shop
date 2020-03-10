package ro.fortech.internship.vinylshop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fortech.internship.vinylshop.order.model.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
