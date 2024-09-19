package com.seniorsistemas.project.domain.item.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import com.seniorsistemas.project.domain.item.mapper.ItemMapper;
import com.seniorsistemas.project.domain.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDTO> findAll() {
        return ItemMapper.MAPPER.toDTOList(itemRepository.findAll());
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
}
