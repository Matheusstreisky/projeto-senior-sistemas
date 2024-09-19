package com.seniorsistemas.project.controller.item;

import java.net.URI;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import com.seniorsistemas.project.domain.item.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Page<ItemDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(itemService.findAll(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ItemDTO> create(@Valid @RequestBody ItemForm itemForm) {
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
    public ResponseEntity<ItemDTO> update(@PathVariable UUID id, @Valid @RequestBody ItemForm itemForm) {
        itemForm.setId(id);
        return ResponseEntity.ok(itemService.update(itemForm));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivate(@PathVariable UUID id) {
        itemService.inactivate(id);
        return ResponseEntity.noContent().build();
    }
}
