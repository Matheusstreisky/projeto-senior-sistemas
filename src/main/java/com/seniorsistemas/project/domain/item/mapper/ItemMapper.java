package com.seniorsistemas.project.domain.item.mapper;

import java.util.Optional;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

    ItemDTO toDTO(Item item);

    Item toEntity(ItemForm itemForm);

    default Optional<ItemDTO> toOptionalItemDTO(Optional<Item> item) {
        return item.map(this::toDTO);
    }
}
