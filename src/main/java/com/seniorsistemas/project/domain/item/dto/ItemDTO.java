package com.seniorsistemas.project.domain.item.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.TipoItem;

public record ItemDTO(
        UUID id,
        String descricao,
        TipoItem tipo,
        BigDecimal valor
) {
}
