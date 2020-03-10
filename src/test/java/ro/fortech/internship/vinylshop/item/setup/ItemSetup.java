package ro.fortech.internship.vinylshop.item.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.item.dto.CreateOrUpdateItemDto;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.item.service.ItemService;

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
}
