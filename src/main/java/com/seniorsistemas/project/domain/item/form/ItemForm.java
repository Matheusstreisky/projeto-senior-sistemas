package com.seniorsistemas.project.domain.item.form;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.TipoItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {

    private UUID id;
    private String descricao;
    private TipoItem tipo;
    private BigDecimal valor;
    private boolean ativo;
}
