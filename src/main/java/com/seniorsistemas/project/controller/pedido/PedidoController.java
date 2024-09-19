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

    ResponseEntity<Page<PedidoDTO>> findAll(Pageable pageable);

    ResponseEntity<PedidoDTO> findById(UUID id);

    ResponseEntity<PedidoDTO> create(PedidoForm pedidoForm);

    ResponseEntity<PedidoDTO> update(UUID id, PedidoForm pedidoForm);

    ResponseEntity<Void> delete(UUID id);

    ResponseEntity<Void> inactivate(UUID id);

    ResponseEntity<Void> close(UUID id);
}
