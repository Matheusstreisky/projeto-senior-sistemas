package com.seniorsistemas.project.domain.itempedido.form;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoForm {

    private UUID id;
    private PedidoDTO pedido;
    private ItemDTO item;
    private Integer quantidade;
}
