package ro.fortech.internship.vinylshop.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.common.exception.ItemNotFoundException;
import ro.fortech.internship.vinylshop.item.converter.DtoConverter;
import ro.fortech.internship.vinylshop.item.dto.CreateOrUpdateItemDto;
import ro.fortech.internship.vinylshop.item.dto.ItemDisplayDto;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.model.ItemStatus;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;
import ro.fortech.internship.vinylshop.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public List<ItemDisplayDto> get() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(DtoConverter::toItemDisplayDtoFromItem)
                .collect(Collectors.toList());
    }

    public void create(CreateOrUpdateItemDto createOrUpdateItemDto) {
        Item item = new Item();
        DtoConverter.toItemFromCreateItemDto(item, createOrUpdateItemDto);

        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidException("That item already is in stock.");
        }
    }

    public void remove(UUID itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        item.setQuantity(0);
        item.setStatus(ItemStatus.DELETED);
        itemRepository.save(item);
    }

    public void update(UUID itemId, CreateOrUpdateItemDto createOrUpdateItemDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        DtoConverter.toItemFromCreateItemDto(item, createOrUpdateItemDto);

        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidException("That item already is in stock.");
        }
    }


}
