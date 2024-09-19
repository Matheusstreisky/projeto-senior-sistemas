package com.seniorsistemas.project.domain.pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;

public record PedidoDTO(
        UUID id,
        LocalDateTime data,
        BigDecimal desconto,
        SituacaoPedido situacao,
        boolean ativo,
        BigDecimal valorTotal,
        BigDecimal valorDesconto,
        List<ItemPedidoDTO> itens
) {
    public PedidoDTO withValorTotal(BigDecimal valorTotal) {
        return new PedidoDTO(id, data, desconto, situacao, ativo, valorTotal, valorDesconto, itens);
    }

    public PedidoDTO withValorDesconto(BigDecimal valorDesconto) {
        return new PedidoDTO(id, data, desconto, situacao, ativo, valorTotal, valorDesconto, itens);
    }
}
