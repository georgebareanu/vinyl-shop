package ro.fortech.internship.vinylshop.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fortech.internship.vinylshop.item.model.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    Item findByName(String name);
}
