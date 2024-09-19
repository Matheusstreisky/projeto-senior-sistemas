package com.seniorsistemas.project.controller.item;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ItemController {

    ResponseEntity<Page<ItemDTO>> findAll(Pageable pageable);

    ResponseEntity<ItemDTO> findById(UUID id);

    ResponseEntity<ItemDTO> create(ItemForm itemForm);

    ResponseEntity<ItemDTO> update(UUID id, ItemForm itemForm);

    ResponseEntity<Void> delete(UUID id);

    ResponseEntity<Void> inactivate(UUID id);
}
