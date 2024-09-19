package com.seniorsistemas.project.domain.itempedido.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_pedido")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer quantidade;
}
