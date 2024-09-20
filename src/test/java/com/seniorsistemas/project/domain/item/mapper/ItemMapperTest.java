package com.seniorsistemas.project.domain.item.mapper;

import java.math.BigDecimal;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemMapperTest {

    private static final ItemMapper MAPPER = ItemMapper.MAPPER;

    @Test
    void shouldMapItemToItemDTO() {
        Item item = new Item();
        item.setId(UUID.randomUUID());
        item.setDescricao("Item Teste");
        item.setTipo(TipoItem.PRODUTO);
        item.setValor(BigDecimal.valueOf(100));
        item.setAtivo(true);

        ItemDTO itemDTO = MAPPER.toDTO(item);

        Assertions.assertNotNull(itemDTO);
        Assertions.assertEquals(item.getId(), itemDTO.id());
        Assertions.assertEquals(item.getDescricao(), itemDTO.descricao());
        Assertions.assertEquals(item.getTipo(), itemDTO.tipo());
        Assertions.assertEquals(item.getValor(), itemDTO.valor());
        Assertions.assertEquals(item.isAtivo(), itemDTO.ativo());
    }

    @Test
    void shouldMapItemFormToItemEntity() {
        ItemForm itemForm = new ItemForm();
        itemForm.setId(UUID.randomUUID());
        itemForm.setDescricao("Item Form Teste");
        itemForm.setTipo(TipoItem.SERVICO);
        itemForm.setValor(BigDecimal.valueOf(150));
        itemForm.setAtivo(true);

        Item item = MAPPER.toEntity(itemForm);

        Assertions.assertNotNull(item);
        Assertions.assertEquals(itemForm.getId(), item.getId());
        Assertions.assertEquals(itemForm.getDescricao(), item.getDescricao());
        Assertions.assertEquals(itemForm.getTipo(), item.getTipo());
        Assertions.assertEquals(itemForm.getValor(), item.getValor());
        Assertions.assertEquals(itemForm.isAtivo(), item.isAtivo());
    }

    @Test
    void shouldReturnNullItemIsNull() {
        ItemDTO itemDTO = MAPPER.toDTO(null);
        Assertions.assertNull(itemDTO);
    }

    @Test
    void shouldReturnNullItemFormIsNull() {
        Item item = MAPPER.toEntity(null);
        Assertions.assertNull(item);
    }
}
