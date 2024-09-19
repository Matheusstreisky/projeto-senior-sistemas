package com.seniorsistemas.project.domain.item.repository;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("SELECT i FROM Item i WHERE " +
            "(:descricao IS NULL OR i.descricao LIKE %:descricao%) AND " +
            "(:tipo IS NULL OR i.tipo = :tipo)")
    Page<Item> findAll(
            @Param("descricao") String descricao,
            @Param("tipo") TipoItem tipo,
            Pageable pageable);
}
