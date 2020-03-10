package ro.fortech.internship.vinylshop.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.item.dto.CreateOrUpdateItemDto;
import ro.fortech.internship.vinylshop.item.service.ItemService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/vinyls")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateOrUpdateItemDto createOrUpdateItemDto) {
        itemService.create(createOrUpdateItemDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        itemService.remove(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable UUID id, @Valid @RequestBody CreateOrUpdateItemDto createOrUpdateItemDto) {
        itemService.update(id, createOrUpdateItemDto);
    }
}
