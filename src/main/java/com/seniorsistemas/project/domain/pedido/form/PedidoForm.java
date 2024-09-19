package com.seniorsistemas.project.domain.pedido.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoForm(
        UUID id,
        LocalDateTime data,
        BigDecimal desconto
) {
}
