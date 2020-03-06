package ro.fortech.internship.vinylshop.item.converter;

import ro.fortech.internship.vinylshop.item.dto.CreateItemDto;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.model.ItemStatus;

public class DtoConverter {
    public static Item toItemFromCreateItemDto(CreateItemDto createItemDto) {
        Item item = new Item();
        item.setName(createItemDto.getName());
        item.setPrice(createItemDto.getPrice());
        if (createItemDto.getStock() == 0) {
            item.setQuantity(0);
            item.setStatus(ItemStatus.INSUFFICIENT_STOCK);
        } else {
            item.setQuantity(createItemDto.getStock());
            item.setStatus(ItemStatus.ACTIVE);
        }
        return item;
    }
}
