package com.mihneacristian.project_tracker.RestControllers;

import com.mihneacristian.project_tracker.DTO.ItemDTO;
import com.mihneacristian.project_tracker.Entities.Item;
import com.mihneacristian.project_tracker.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> allItemsDTO = itemService.getAllItems();
        return ResponseEntity.ok(allItemsDTO);
    }

    @GetMapping(value = "/item/id/{itemId}", produces = "application/json")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Integer itemId) {
        try {
            Item itemById = itemService.findByItemId(itemId);
            ItemDTO itemDTO = new ItemDTO(itemById);
            return ResponseEntity.ok(itemDTO);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PostMapping(value = "/add-new-item", consumes = "application/json")
    public ResponseEntity<Item> createItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemService.saveNewItem(itemDTO);
        return ResponseEntity.ok(item);
    }

    @PutMapping(value = "/update-item/{itemId}", consumes = "application/json")
    public ResponseEntity<Item> updateItemById(@PathVariable(name = "itemId") Integer itemId, @RequestBody ItemDTO itemDTO) {
        try {
            Item updatedItem = itemService.updateItemById(itemId, itemDTO);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Integer itemId) {
        try {
            itemService.deleteItemById(itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
