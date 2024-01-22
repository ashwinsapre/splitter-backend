package com.app.splitter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/item")
public class ItemController {
    private final ItemService service;
    private final ItemRepository itemRepository;
    @Autowired
    public ItemController(ItemService service, ItemRepository itemRepository){
        this.service = service;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<List<Item>> readJson(String path) {
        try {
            List<Item> items = service.readJsonFile(path);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void insertItem(@RequestBody Item i) {
        itemRepository.save(i);
    }
}
