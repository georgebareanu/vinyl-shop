package ro.fortech.internship.vinylshop.order.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.order.repository.OrderRepository;

@Component
public class OrderSetup {

    @Autowired
    private OrderRepository orderRepository;

    public void tearDown() {
        orderRepository.deleteAll();
    }
}
