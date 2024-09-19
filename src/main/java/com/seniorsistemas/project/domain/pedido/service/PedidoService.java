package com.seniorsistemas.project.domain.pedido.service;

import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService {

    Page<PedidoDTO> findAll(Pageable pageable);

    PedidoDTO findById(UUID id);

    PedidoDTO save(PedidoForm pedidoForm);

    PedidoDTO update(PedidoForm pedidoForm);

    void delete(UUID id);

    void inactivate(UUID id);

    void close(UUID id);

    void validate(Pedido pedido);

    void validateNotFound(UUID id);
}
