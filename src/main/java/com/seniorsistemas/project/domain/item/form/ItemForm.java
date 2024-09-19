package com.seniorsistemas.project.domain.item.form;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.TipoItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {

    private UUID id;

    @NotBlank
    private String descricao;

    @NotNull
    private TipoItem tipo;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal valor;

    private boolean ativo;
}
