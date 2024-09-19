package com.seniorsistemas.project.domain.itempedido.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemPedidoService {

    Optional<ItemPedidoDTO> findById(UUID id);

    Page<ItemPedidoDTO> findByPedido(UUID pedidoId, Pageable pageable);

    ItemPedidoDTO save(ItemPedidoForm itemPedidoForm);

    void delete(UUID id);
}
