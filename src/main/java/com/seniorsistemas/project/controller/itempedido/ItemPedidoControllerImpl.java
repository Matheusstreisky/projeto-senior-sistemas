package com.seniorsistemas.project.controller.itempedido;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.itempedido.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/itempedido")
public class ItemPedidoControllerImpl implements ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Override
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Page<ItemPedidoDTO>> findByPedido(UUID pedidoId, Pageable pageable) {
        return ResponseEntity.ok(itemPedidoService.findByPedido(pedidoId, pageable));
    }

    @Override
    @PostMapping
    public ResponseEntity<ItemPedidoDTO> create(ItemPedidoForm itemPedidoForm) {
        try {
            ItemPedidoDTO createdItemPedido = itemPedidoService.save(itemPedidoForm);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdItemPedido.id())
                    .toUri();

            return ResponseEntity.created(location).body(createdItemPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> update(UUID id, ItemPedidoForm itemPedidoForm) {
        try {
            Optional<ItemPedidoDTO> existingItemPedido = itemPedidoService.findById(id);

            if (existingItemPedido.isPresent()) {
                return ResponseEntity.ok(itemPedidoService.save(itemPedidoForm));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(UUID id) {
        Optional<ItemPedidoDTO> existingItemPedido = itemPedidoService.findById(id);

        if (existingItemPedido.isPresent()) {
            itemPedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
