package ro.fortech.internship.vinylshop.item.converter;

import ro.fortech.internship.vinylshop.item.dto.CreateOrUpdateItemDto;
import ro.fortech.internship.vinylshop.item.dto.ItemDisplayDto;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.model.ItemStatus;

public class DtoConverter {
    public static Item toItemFromCreateItemDto(Item item, CreateOrUpdateItemDto createOrUpdateItemDto) {
        item.setName(createOrUpdateItemDto.getName());
        item.setPrice(createOrUpdateItemDto.getPrice());
        if (createOrUpdateItemDto.getStock() == 0) {
            item.setQuantity(0);
            item.setStatus(ItemStatus.INSUFFICIENT_STOCK);
        } else {
            item.setQuantity(createOrUpdateItemDto.getStock());
            item.setStatus(ItemStatus.ACTIVE);
        }
        return item;
    }

    public static ItemDisplayDto toItemDisplayDtoFromItem(Item item) {
        ItemDisplayDto itemDisplayDto = new ItemDisplayDto();
        itemDisplayDto.setId(item.getId());
        itemDisplayDto.setName(item.getName());
        itemDisplayDto.setPrice(item.getPrice());
        itemDisplayDto.setQuantity(item.getQuantity());
        itemDisplayDto.setStatus(item.getStatus());
        return itemDisplayDto;
    }
}
