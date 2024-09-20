package com.seniorsistemas.project.domain.pedido.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class PedidoForm {

    private UUID id;

    @NotNull
    @PastOrPresent
    private LocalDateTime data;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal desconto;

    @NotNull
    private SituacaoPedido situacao;

    private boolean ativo;
}
