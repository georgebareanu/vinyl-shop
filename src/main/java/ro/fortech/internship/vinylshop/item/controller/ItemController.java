package ro.fortech.internship.vinylshop.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.fortech.internship.vinylshop.item.dto.CreateItemDto;
import ro.fortech.internship.vinylshop.item.service.ItemService;

import javax.validation.Valid;

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
    public void create(@Valid @RequestBody CreateItemDto createItemDto) {
        itemService.create(createItemDto);
    }
}
