package com.seniorsistemas.project.controller.pedido;

import java.util.List;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PedidoController {

    @GetMapping
    ResponseEntity<List<PedidoDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<PedidoDTO> findById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<PedidoDTO> create(@RequestBody PedidoForm pedidoForm);

    @PutMapping("/{id}")
    ResponseEntity<PedidoDTO> update(@PathVariable UUID id, @RequestBody PedidoForm pedidoForm);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
