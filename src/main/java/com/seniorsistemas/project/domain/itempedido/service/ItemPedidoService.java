package com.seniorsistemas.project.domain.itempedido.service;

import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemPedidoService {

    Page<ItemPedidoDTO> findByPedido(UUID pedidoId, Pageable pageable);

    ItemPedidoDTO findById(UUID id);

    ItemPedidoDTO save(ItemPedidoForm itemPedidoForm) throws Exception;

    ItemPedidoDTO update(ItemPedidoForm itemPedidoForm) throws Exception;

    void delete(UUID id);

    void validate(ItemPedido itemPedido) throws Exception;

    void validateNotFound(UUID id);
}
