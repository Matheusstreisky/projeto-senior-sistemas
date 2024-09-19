package com.seniorsistemas.project.controller.itempedido;

import java.util.List;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ItemPedidoController {

    @GetMapping("/{pedidoId}")
    ResponseEntity<List<ItemPedidoDTO>> findByPedido(@PathVariable UUID pedidoId);

    @PostMapping
    ResponseEntity<ItemPedidoDTO> create(@RequestBody ItemPedidoForm itemPedidoForm);

    @PutMapping("/{id}")
    ResponseEntity<ItemPedidoDTO> update(@PathVariable UUID id, @RequestBody ItemPedidoForm itemPedidoForm);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
