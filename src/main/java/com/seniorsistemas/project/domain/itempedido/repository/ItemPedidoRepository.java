package com.seniorsistemas.project.domain.itempedido.repository;

import java.util.List;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    List<ItemPedido> findByPedido_Id(UUID pedidoId);
}
