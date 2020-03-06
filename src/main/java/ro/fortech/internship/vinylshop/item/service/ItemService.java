package ro.fortech.internship.vinylshop.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.fortech.internship.vinylshop.common.exception.InvalidException;
import ro.fortech.internship.vinylshop.item.converter.DtoConverter;
import ro.fortech.internship.vinylshop.item.dto.CreateItemDto;
import ro.fortech.internship.vinylshop.item.model.Item;
import ro.fortech.internship.vinylshop.item.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void create(CreateItemDto createItemDto) {
        Item item = DtoConverter.toItemFromCreateItemDto(createItemDto);

        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidException("That item already is in stock.");
        }

    }
}