package com.seniorsistemas.project.domain.item.form;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.TipoItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
