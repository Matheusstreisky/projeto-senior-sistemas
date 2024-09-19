package com.seniorsistemas.project.domain.item.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.form.ItemForm;

public interface ItemService {

    List<ItemDTO> findAll();

    Optional<ItemDTO> findById(UUID id);

    ItemDTO save(ItemForm itemDTOForm);

    void delete(UUID id);
}
