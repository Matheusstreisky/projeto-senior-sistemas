package com.seniorsistemas.project.domain.item.service;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Page<ItemDTO> findAll(String descricao, TipoItem tipo, Pageable pageable);

    ItemDTO findById(UUID id);

    ItemDTO save(ItemForm itemForm);

    ItemDTO update(ItemForm itemForm);

    void delete(UUID id);

    void inactivate(UUID id);

    void validateNotFound(UUID id);
}
