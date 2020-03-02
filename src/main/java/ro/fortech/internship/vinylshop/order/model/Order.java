package ro.fortech.internship.vinylshop.order.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "order_tab")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
