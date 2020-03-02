package ro.fortech.internship.vinylshop.cartitem.model;

import lombok.*;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.item.model.Item;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    private Cart cart;

    private Integer quantity;

    private UUID orderId;
}