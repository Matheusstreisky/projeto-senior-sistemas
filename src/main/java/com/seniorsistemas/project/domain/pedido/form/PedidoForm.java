package com.seniorsistemas.project.domain.pedido.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;

public record PedidoForm(
        UUID id,
        LocalDateTime data,
        BigDecimal desconto,
        SituacaoPedido situacao,
        boolean ativo
) {
}
