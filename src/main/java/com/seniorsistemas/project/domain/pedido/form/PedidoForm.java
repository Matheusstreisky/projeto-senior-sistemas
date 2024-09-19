package com.seniorsistemas.project.domain.pedido.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoForm {

    private UUID id;
    private LocalDateTime data;
    private BigDecimal desconto;
    private SituacaoPedido situacao;
    private boolean ativo;
}
