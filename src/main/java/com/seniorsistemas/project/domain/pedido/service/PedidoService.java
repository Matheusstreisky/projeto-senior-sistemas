package com.seniorsistemas.project.domain.pedido.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;

public interface PedidoService {

    List<PedidoDTO> findAll();

    Optional<PedidoDTO> findById(UUID id);

    PedidoDTO save(PedidoForm pedidoForm);

    void delete(UUID id);
}
