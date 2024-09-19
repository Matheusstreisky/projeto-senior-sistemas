package com.seniorsistemas.project.domain.pedido.repository;

import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
