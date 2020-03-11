package ro.fortech.internship.vinylshop.order.converter;

import ro.fortech.internship.vinylshop.order.dto.DisplayOrderDto;
import ro.fortech.internship.vinylshop.order.model.Order;

public class OrderDtoConverter {
    public static DisplayOrderDto toDisplayOrderDtoFromOrder(Order order) {
        DisplayOrderDto displayOrderDto = new DisplayOrderDto();
        displayOrderDto.setCost(order.getCost());
        displayOrderDto.setDateCreate(order.getDateCreated());
        displayOrderDto.setStatus(order.getStatus());
        return displayOrderDto;
    }
}
