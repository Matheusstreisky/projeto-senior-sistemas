package com.seniorsistemas.project.controller.itempedido;

import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ItemPedidoController {

    ResponseEntity<Page<ItemPedidoDTO>> findByPedido(UUID pedidoId, Pageable pageable);

    ResponseEntity<ItemPedidoDTO> findById(UUID id);

    ResponseEntity<ItemPedidoDTO> create(ItemPedidoForm itemPedidoForm);

    ResponseEntity<ItemPedidoDTO> update(UUID id, ItemPedidoForm itemPedidoForm) throws Exception;

    ResponseEntity<Void> delete(UUID id);
}
