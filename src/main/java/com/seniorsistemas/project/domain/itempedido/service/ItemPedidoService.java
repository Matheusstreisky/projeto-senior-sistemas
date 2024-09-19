package com.seniorsistemas.project.domain.itempedido.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;

public interface ItemPedidoService {

    Optional<ItemPedidoDTO> findById(UUID id);

    List<ItemPedidoDTO> findByPedido(UUID pedidoId);

    ItemPedidoDTO save(ItemPedidoForm itemPedidoForm);

    void delete(UUID id);
}
