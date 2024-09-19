package com.seniorsistemas.project.domain.itempedido.form;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;

public record ItemPedidoForm(
        UUID id,
        PedidoDTO pedido,
        ItemDTO item,
        Integer quantidade
) {
}
