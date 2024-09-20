package com.seniorsistemas.project.domain.pedido.repository;

import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Query("SELECT p FROM Pedido p WHERE " +
            "(:situacao IS NULL OR p.situacao = :situacao)")
    Page<Pedido> findAll(
            @Param("situacao") SituacaoPedido situacao,
            Pageable pageable);
}
