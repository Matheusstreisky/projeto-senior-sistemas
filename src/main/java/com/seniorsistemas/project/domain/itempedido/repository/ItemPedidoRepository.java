package com.seniorsistemas.project.domain.itempedido.repository;

import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    Page<ItemPedido> findByPedido_Id(UUID pedidoId, Pageable pageable);
}
