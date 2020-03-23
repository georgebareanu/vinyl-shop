package ro.fortech.internship.vinylshop.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fortech.internship.vinylshop.item.model.Item;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    Item findByName(String name);
}
