package com.seniorsistemas.project.domain.item.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
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
    public Page<ItemDTO> findAll(Pageable pageable) {
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return itemPage.map(ItemMapper.MAPPER::toDTO);
    }

    @Override
    public Optional<ItemDTO> findById(UUID id) {
        return ItemMapper.MAPPER.toOptionalItemDTO(itemRepository.findById(id));
    }

    @Override
    public ItemDTO save(ItemForm itemForm) {
        Item item = ItemMapper.MAPPER.toEntity(itemForm);
        return ItemMapper.MAPPER.toDTO(itemRepository.save(item));
    }

    @Override
    public void delete(UUID id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void inactivate(UUID id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        optionalItem.ifPresent(item -> {
            item.setAtivo(false);
            itemRepository.save(item);
        });
    }
}
