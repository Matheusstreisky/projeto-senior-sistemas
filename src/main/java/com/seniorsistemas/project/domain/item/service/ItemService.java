package com.seniorsistemas.project.domain.item.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Page<ItemDTO> findAll(Pageable pageable);

    Optional<ItemDTO> findById(UUID id);

    ItemDTO save(ItemForm itemDTOForm);

    void delete(UUID id);
}
