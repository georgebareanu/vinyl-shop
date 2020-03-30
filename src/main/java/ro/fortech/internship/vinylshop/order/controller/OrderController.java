package ro.fortech.internship.vinylshop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.order.model.Order;
import ro.fortech.internship.vinylshop.order.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create() {
        orderService.create();
    }
}
