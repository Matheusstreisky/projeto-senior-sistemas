package com.seniorsistemas.project.domain.itempedido.dto;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;

public record ItemPedidoDTO(
        UUID id,
        ItemDTO item,
        Integer quantidade
) {
}
