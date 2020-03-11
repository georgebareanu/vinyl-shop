package ro.fortech.internship.vinylshop.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.order.model.Order;
import ro.fortech.internship.vinylshop.order.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders(@RequestHeader UUID userId) {
        return orderService.getOrders(userId);
    }
}
