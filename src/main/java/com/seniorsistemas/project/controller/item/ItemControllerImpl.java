package com.seniorsistemas.project.controller.item;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import com.seniorsistemas.project.domain.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/item")
public class ItemControllerImpl implements ItemController {

    @Autowired
    private ItemService itemService;

    @Override
    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(UUID id) {
        Optional<ItemDTO> itemDTO = itemService.findById(id);
        return itemDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<ItemDTO> create(ItemForm itemForm) {
        ItemDTO createdItem = itemService.save(itemForm);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdItem.id())
                .toUri();

        return ResponseEntity.created(location).body(createdItem);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(UUID id, ItemForm itemForm) {
        Optional<ItemDTO> existingItem = itemService.findById(id);

        if (existingItem.isPresent()) {
            return ResponseEntity.ok(itemService.save(itemForm));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(UUID id) {
        Optional<ItemDTO> existingItem = itemService.findById(id);

        if (existingItem.isPresent()) {
            itemService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
