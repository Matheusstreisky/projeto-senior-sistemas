package com.seniorsistemas.project.domain.itempedido.form;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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
