package com.seniorsistemas.project.controller.pedido;

import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PedidoController {

    @GetMapping
    ResponseEntity<Page<PedidoDTO>> findAll(@PageableDefault(size = 10) Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<PedidoDTO> findById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<PedidoDTO> create(@RequestBody PedidoForm pedidoForm);

    @PutMapping("/{id}")
    ResponseEntity<PedidoDTO> update(@PathVariable UUID id, @RequestBody PedidoForm pedidoForm);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);

    @PutMapping("/{id}")
    ResponseEntity<Void> inactivate(@PathVariable UUID id);
}
