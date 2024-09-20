package com.seniorsistemas.project.domain.pedido.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PedidoMapperTest {

    private static final PedidoMapper MAPPER = PedidoMapper.MAPPER;

    @Test
    void shouldMapPedidoToPedidoDTO() {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setData(LocalDateTime.now());
        pedido.setDesconto(BigDecimal.valueOf(10));
        pedido.setSituacao(SituacaoPedido.ABERTO);
        pedido.setAtivo(true);

        PedidoDTO pedidoDTO = MAPPER.toDTO(pedido);

        Assertions.assertNotNull(pedidoDTO);
        Assertions.assertEquals(pedido.getId(), pedidoDTO.id());
        Assertions.assertEquals(pedido.getData(), pedidoDTO.data());
        Assertions.assertEquals(pedido.getDesconto(), pedidoDTO.desconto());
        Assertions.assertEquals(pedido.getSituacao(), pedidoDTO.situacao());
        Assertions.assertEquals(pedido.isAtivo(), pedidoDTO.ativo());
    }

    @Test
    void shouldMapPedidoFormToPedidoEntity() {
        PedidoForm pedidoForm = new PedidoForm();
        pedidoForm.setId(UUID.randomUUID());
        pedidoForm.setData(LocalDateTime.now());
        pedidoForm.setDesconto(BigDecimal.valueOf(10));
        pedidoForm.setSituacao(SituacaoPedido.ABERTO);
        pedidoForm.setAtivo(true);

        Pedido pedido = MAPPER.toEntity(pedidoForm);

        Assertions.assertNotNull(pedido);
        Assertions.assertEquals(pedidoForm.getId(), pedido.getId());
        Assertions.assertEquals(pedidoForm.getData(), pedido.getData());
        Assertions.assertEquals(pedidoForm.getDesconto(), pedido.getDesconto());
        Assertions.assertEquals(pedidoForm.getSituacao(), pedido.getSituacao());
        Assertions.assertEquals(pedidoForm.isAtivo(), pedido.isAtivo());
    }

    @Test
    void shouldReturnNullWhenPedidoIsNull() {
        PedidoDTO pedidoDTO = MAPPER.toDTO(null);
        Assertions.assertNull(pedidoDTO);
    }

    @Test
    void shouldReturnNullWhenPedidoFormIsNull() {
        Pedido pedido = MAPPER.toEntity(null);
        Assertions.assertNull(pedido);
    }
}
