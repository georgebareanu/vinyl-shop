package ro.fortech.internship.vinylshop.item.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.item.dto.CreateOrUpdateItemDto;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.item.service.ItemService;

import java.util.UUID;

@Component
public class ItemSetup {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    public void deleteItemsFromDb() {
        itemRepository.deleteAll();
    }

    public CreateOrUpdateItemDto createItemDto(int stock, String name) {
        return new CreateOrUpdateItemDto(name, 15.5, stock);
    }

    public void createValidItemAndSave() {
        CreateOrUpdateItemDto createOrUpdateItemDto = createItemDto(15, "Bob Marley");
        itemService.create(createOrUpdateItemDto);
    }

    public void createItemWithZeroQuantityAndSave() {
        CreateOrUpdateItemDto createOrUpdateItemDto = createItemDto(0, "Bob Marley");
        itemService.create(createOrUpdateItemDto);
    }

    public void createThenUpdateItemAndSave() {
        createValidItemAndSave();
        Item item = itemRepository.findByName("Bob Marley");
        UUID itemId = item.getId();
        CreateOrUpdateItemDto updateItem = createItemDto(40, "Guns'N'Roses");
        itemService.update(itemId, updateItem);
    }
}
