package com.seniorsistemas.project.domain.item.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import com.seniorsistemas.project.domain.item.mapper.ItemMapper;
import com.seniorsistemas.project.domain.item.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ItemServiceTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    private Item item;
    private ItemDTO itemDTO;
    private ItemForm itemForm;

    @BeforeEach
    void setUp() {
        item = new Item(UUID.randomUUID(), "Produto Teste", TipoItem.PRODUTO, BigDecimal.valueOf(100), true);
        itemDTO = new ItemDTO(item.getId(), item.getDescricao(), item.getTipo(), item.getValor(), item.isAtivo());
        itemForm = new ItemForm(item.getId(), item.getDescricao(), item.getTipo(), item.getValor(), item.isAtivo());
    }

    @Test
    void shouldFindAllItems() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> itemPage = new PageImpl<>(List.of(item), pageable, 1);
        Mockito.when(itemRepository.findAll(null, null, pageable)).thenReturn(itemPage);
        Mockito.when(itemMapper.toDTO(item)).thenReturn(itemDTO);

        Page<ItemDTO> result = itemService.findAll(null, null, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(itemDTO, result.getContent().get(0));
        Mockito.verify(itemRepository, Mockito.times(1)).findAll(null, null, pageable);
    }

    @Test
    void shouldFindItemById() {
        UUID id = item.getId();
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        Mockito.when(itemMapper.toDTO(item)).thenReturn(itemDTO);

        ItemDTO result = itemService.findById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.id());
        Mockito.verify(itemRepository, Mockito.times(2)).findById(id);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenItemNotFound() {
        UUID id = UUID.randomUUID();
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> itemService.findById(id));

        Mockito.verify(itemRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void shouldSaveNewItem() {
        Mockito.when(itemMapper.toEntity(itemForm)).thenReturn(item);
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Mockito.when(itemMapper.toDTO(item)).thenReturn(itemDTO);

        ItemDTO result = itemService.save(itemForm);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(item.getId(), result.id());
        Assertions.assertTrue(item.isAtivo());
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }

    @Test
    void shouldUpdateItem() {
        UUID id = item.getId();
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        Mockito.when(itemMapper.toEntity(itemForm)).thenReturn(item);
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Mockito.when(itemMapper.toDTO(item)).thenReturn(itemDTO);

        ItemDTO result = itemService.update(itemForm);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.id());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(id);
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }

    @Test
    void shouldDeleteItem() {
        UUID id = item.getId();
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        itemService.delete(id);

        Mockito.verify(itemRepository, Mockito.times(1)).findById(id);
        Mockito.verify(itemRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void shouldInactivateItem() {
        UUID id = item.getId();
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        itemService.inactivate(id);

        Assertions.assertFalse(item.isAtivo());
        Mockito.verify(itemRepository, Mockito.times(2)).findById(id);
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }
}
