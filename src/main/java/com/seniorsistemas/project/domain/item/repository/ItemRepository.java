package com.seniorsistemas.project.domain.item.repository;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
