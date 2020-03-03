package ro.fortech.internship.vinylshop.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.fortech.internship.vinylshop.cart.model.Cart;
import ro.fortech.internship.vinylshop.order.model.Order;
import ro.fortech.internship.vinylshop.role.model.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tab")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Size(min = 3, max = 25)
    @NotNull
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 25)
    @NotNull
    @NotBlank
    private String lastName;

    @Email
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String email;

    @Column(name = "pwd", length = 500)
    @NotBlank
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
}