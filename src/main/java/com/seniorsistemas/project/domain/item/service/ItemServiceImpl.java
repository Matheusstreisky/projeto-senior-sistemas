package com.seniorsistemas.project.domain.item.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import com.seniorsistemas.project.domain.item.mapper.ItemMapper;
import com.seniorsistemas.project.domain.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Page<ItemDTO> findAll(String descricao, TipoItem tipo, Pageable pageable) {
        Page<Item> itemPage = itemRepository.findAll(descricao, tipo, pageable);
        return itemPage.map(ItemMapper.MAPPER::toDTO);
    }

    @Override
    public ItemDTO findById(UUID id) {
        validateNotFound(id);
        return ItemMapper.MAPPER.toDTO(itemRepository.findById(id).get());
    }

    @Override
    public ItemDTO save(ItemForm itemForm) {
        Item item = ItemMapper.MAPPER.toEntity(itemForm);
        item.setAtivo(true);
        return ItemMapper.MAPPER.toDTO(itemRepository.save(item));
    }

    @Override
    public ItemDTO update(ItemForm itemForm) {
        validateNotFound(itemForm.getId());

        Item item = ItemMapper.MAPPER.toEntity(itemForm);
        return ItemMapper.MAPPER.toDTO(itemRepository.save(item));
    }

    @Override
    public void delete(UUID id) {
        validateNotFound(id);
        itemRepository.deleteById(id);
    }

    @Override
    public void inactivate(UUID id) {
        validateNotFound(id);

        Item item = itemRepository.findById(id).get();
        item.setAtivo(false);
        itemRepository.save(item);
    }

    @Override
    public void validateNotFound(UUID id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new NotFoundException(id);
        }
    }
}
