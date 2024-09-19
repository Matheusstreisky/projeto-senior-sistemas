package com.seniorsistemas.project.domain.item.form;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.TipoItem;

public record ItemForm(
        UUID id,
        String descricao,
        TipoItem tipo,
        BigDecimal valor,
        boolean ativo
) {
}
