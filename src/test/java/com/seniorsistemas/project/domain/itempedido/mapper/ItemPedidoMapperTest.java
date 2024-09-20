package com.seniorsistemas.project.domain.itempedido.mapper;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemPedidoMapperTest {

    private static final ItemPedidoMapper MAPPER = ItemPedidoMapper.MAPPER;

    @Test
    void shouldMapItemPedidoToItemPedidoDTO() {
        Item item = new Item();
        item.setId(UUID.randomUUID());

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(UUID.randomUUID());
        itemPedido.setItem(item);
        itemPedido.setQuantidade(5);

        ItemPedidoDTO itemPedidoDTO = MAPPER.toDTO(itemPedido);

        Assertions.assertNotNull(itemPedidoDTO);
        Assertions.assertEquals(itemPedidoDTO.id(), itemPedidoDTO.id());
        Assertions.assertNotNull(itemPedidoDTO.item());
        Assertions.assertEquals(item.getId(), itemPedidoDTO.item().id());
        Assertions.assertEquals(itemPedidoDTO.quantidade(), itemPedidoDTO.quantidade());
    }

    @Test
    void shouldMapItemPedidoFormToItemPedidoEntity() {
        PedidoDTO pedidoDTO = new PedidoDTO(UUID.randomUUID(), null, null, null, true, BigDecimal.ZERO, BigDecimal.ZERO, null);
        ItemDTO itemDTO = new ItemDTO(UUID.randomUUID(), null, null, null, true);

        ItemPedidoForm itemPedidoForm = new ItemPedidoForm();
        itemPedidoForm.setId(UUID.randomUUID());
        itemPedidoForm.setPedido(pedidoDTO);
        itemPedidoForm.setItem(itemDTO);
        itemPedidoForm.setQuantidade(10);

        ItemPedido itemPedido = MAPPER.toEntity(itemPedidoForm);

        Assertions.assertNotNull(itemPedido);
        Assertions.assertEquals(itemPedido.getId(), itemPedido.getId());
        Assertions.assertNotNull(itemPedido.getPedido());
        Assertions.assertEquals(pedidoDTO.id(), itemPedido.getPedido().getId());
        Assertions.assertNotNull(itemPedido.getItem());
        Assertions.assertEquals(itemDTO.id(), itemPedido.getItem().getId());
        Assertions.assertEquals(itemPedido.getQuantidade(), itemPedido.getQuantidade());
    }

    @Test
    void shouldReturnNullWheItemPedidoIsNull() {
        ItemPedidoDTO itemPedidoDTO = MAPPER.toDTO(null);
        Assertions.assertNull(itemPedidoDTO);
    }

    @Test
    void shouldReturnNullWhenItemPedidoFormIsNull() {
        ItemPedido itemPedido = MAPPER.toEntity(null);
        Assertions.assertNull(itemPedido);
    }
}
