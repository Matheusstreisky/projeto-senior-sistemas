package com.seniorsistemas.project.domain.item.mapper;

import java.util.List;
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

    List<ItemDTO> toDTOList(List<Item> items);

    default Optional<ItemDTO> toOptionalItemDTO(Optional<Item> item) {
        return item.map(this::toDTO);
    }
}
