package com.seniorsistemas.project.controller.pedido;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import com.seniorsistemas.project.domain.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/pedido")
public class PedidoControllerImpl implements PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Override
    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(pedidoService.findAll(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable UUID id) {
        Optional<PedidoDTO> pedidoDTO = pedidoService.findById(id);
        return pedidoDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<PedidoDTO> create(@Valid @RequestBody PedidoForm pedidoForm) {
        PedidoDTO createdPedido = pedidoService.save(pedidoForm);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPedido.id())
                .toUri();

        return ResponseEntity.created(location).body(createdPedido);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> update(@PathVariable UUID id, @Valid @RequestBody PedidoForm pedidoForm) {
        try {
            Optional<PedidoDTO> existingItem = pedidoService.findById(id);

            if (existingItem.isPresent()) {
                pedidoForm.setId(id);
                return ResponseEntity.ok(pedidoService.update(pedidoForm));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Optional<PedidoDTO> existingPedido = pedidoService.findById(id);

        if (existingPedido.isPresent()) {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivate(@PathVariable UUID id) {
        Optional<PedidoDTO> existingItem = pedidoService.findById(id);

        if (existingItem.isPresent()) {
            pedidoService.inactivate(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping("/{id}/close")
    public ResponseEntity<Void> close(@PathVariable UUID id) {
        Optional<PedidoDTO> existingItem = pedidoService.findById(id);

        if (existingItem.isPresent()) {
            pedidoService.close(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
