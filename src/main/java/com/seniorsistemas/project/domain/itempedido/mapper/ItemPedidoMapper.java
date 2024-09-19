package com.seniorsistemas.project.domain.itempedido.mapper;

import java.util.Optional;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemPedidoMapper {

    ItemPedidoMapper MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedidoDTO toDTO(ItemPedido itemPedido);

    ItemPedido toEntity(ItemPedidoForm itemPedidoForm);

    default Optional<ItemPedidoDTO> toOptionalItemDTO(Optional<ItemPedido> itemPedido) {
        return itemPedido.map(this::toDTO);
    }
}
