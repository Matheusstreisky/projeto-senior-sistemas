package com.seniorsistemas.project.domain.itempedido.form;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoForm {

    private UUID id;

    @NotNull
    private PedidoDTO pedido;

    @NotNull
    private ItemDTO item;

    @NotNull
    @Min(value = 1)
    private Integer quantidade;
}
