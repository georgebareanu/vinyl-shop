package ro.fortech.internship.vinylshop.item.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.item.dto.CreateItemDto;
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

    public CreateItemDto createItemDto() {
        return new CreateItemDto("Bob Marley", 15.5, 10);
    }

}
