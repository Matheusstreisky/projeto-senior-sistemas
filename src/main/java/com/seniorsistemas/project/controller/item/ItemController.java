package com.seniorsistemas.project.controller.item;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.form.ItemForm;
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

public interface ItemController {

    @GetMapping
    ResponseEntity<Page<ItemDTO>> findAll(@PageableDefault(size = 10) Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<ItemDTO> findById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<ItemDTO> create(@RequestBody ItemForm itemForm);

    @PutMapping("/{id}")
    ResponseEntity<ItemDTO> update(@PathVariable UUID id, @RequestBody ItemForm itemForm);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
