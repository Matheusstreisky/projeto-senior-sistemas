package com.seniorsistemas.project.domain.itempedido.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.config.validation.exception.ItemIsInactiveException;
import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.config.validation.exception.PedidoIsAlreadyClosedException;
import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.repository.ItemRepository;
import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.itempedido.mapper.ItemPedidoMapper;
import com.seniorsistemas.project.domain.itempedido.repository.ItemPedidoRepository;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import com.seniorsistemas.project.domain.pedido.repository.PedidoRepository;
import com.seniorsistemas.project.domain.pedido.service.PedidoService;
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
class ItemPedidoServiceTest {

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ItemPedidoMapper itemPedidoMapper;

    private ItemPedido itemPedido;
    private ItemPedidoDTO itemPedidoDTO;
    private ItemPedidoForm itemPedidoForm;
    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private Item item;
    private ItemDTO itemDTO;

    @BeforeEach
    void setup() {
        item = new Item(UUID.randomUUID(), "Produto Teste", TipoItem.PRODUTO, BigDecimal.valueOf(100), true);
        pedido = new Pedido(UUID.randomUUID(), null, null, null, null, true);
        itemPedido = new ItemPedido(UUID.randomUUID(), pedido, item, 1);

        itemDTO = new ItemDTO(item.getId(), item.getDescricao(), item.getTipo(), item.getValor(), item.isAtivo());
        pedidoDTO = new PedidoDTO(pedido.getId(), pedido.getData(), pedido.getDesconto(), pedido.getSituacao(), pedido.isAtivo(), BigDecimal.ZERO, BigDecimal.ZERO, null);
        itemPedidoDTO = new ItemPedidoDTO(itemPedido.getId(), itemDTO, itemPedido.getQuantidade());
        itemPedidoForm = new ItemPedidoForm(itemPedido.getId(), pedidoDTO, itemDTO, itemPedido.getQuantidade());
    }

    @Test
    void shouldFindByPedido() {
        UUID pedidoId = pedido.getId();
        Pageable pageable = PageRequest.of(0, 10);
        Page<ItemPedido> itemPedidoPage = new PageImpl<>(List.of(itemPedido), pageable, 1);
        Mockito.when(itemPedidoRepository.findByPedido_Id(pedidoId, pageable)).thenReturn(itemPedidoPage);
        Mockito.when(itemPedidoMapper.toDTO(itemPedido)).thenReturn(itemPedidoDTO);

        Page<ItemPedidoDTO> result = itemPedidoService.findByPedido(pedidoId, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(itemPedidoDTO, result.getContent().get(0));
        Mockito.verify(itemPedidoRepository, Mockito.times(1)).findByPedido_Id(pedidoId, pageable);
    }

    @Test
    void shouldFindById() {
        UUID id = itemPedido.getId();
        Mockito.when(itemPedidoRepository.findById(id)).thenReturn(Optional.of(itemPedido));
        Mockito.when(itemPedidoMapper.toDTO(itemPedido)).thenReturn(itemPedidoDTO);

        ItemPedidoDTO result = itemPedidoService.findById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.id());
        Mockito.verify(itemPedidoRepository, Mockito.times(2)).findById(id);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenItemNotFound() {
        UUID id = UUID.randomUUID();
        Mockito.when(itemPedidoRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> itemPedidoService.findById(id));

        Mockito.verify(itemPedidoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void shouldSaveItemPedido() {
        UUID itemId = item.getId();
        UUID pedidoId = pedido.getId();
        Mockito.when(itemPedidoMapper.toEntity(itemPedidoForm)).thenReturn(itemPedido);
        Mockito.when(itemPedidoRepository.save(itemPedido)).thenReturn(itemPedido);
        Mockito.when(itemPedidoMapper.toDTO(itemPedido)).thenReturn(itemPedidoDTO);
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        ItemPedidoDTO result = itemPedidoService.save(itemPedidoForm);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(itemPedido.getId(), result.id());
        Mockito.verify(itemPedidoRepository, Mockito.times(1)).save(itemPedido);
        Mockito.verify(itemRepository, Mockito.times(1)).findById(itemId);
        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(pedidoId);
    }

    @Test
    void shouldThrowItemIsInactiveExceptionWhenItemIsInactive() {
        UUID itemId = item.getId();
        item.setAtivo(false);

        Mockito.when(itemPedidoMapper.toEntity(itemPedidoForm)).thenReturn(itemPedido);
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Assertions.assertThrows(ItemIsInactiveException.class, () -> itemPedidoService.save(itemPedidoForm));

        Mockito.verify(itemRepository, Mockito.times(1)).findById(itemId);
    }

    @Test
    void shouldThrowPedidoIsAlreadyClosedExceptionWhenPedidoIsAlreadyClosed() {
        UUID itemId = item.getId();
        UUID pedidoId = pedido.getId();
        pedido.setSituacao(SituacaoPedido.FECHADO);

        Mockito.when(itemPedidoMapper.toEntity(itemPedidoForm)).thenReturn(itemPedido);
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        Mockito.doThrow(new PedidoIsAlreadyClosedException()).when(pedidoService).validate(pedido);

        Assertions.assertThrows(PedidoIsAlreadyClosedException.class, () -> itemPedidoService.save(itemPedidoForm));

        Mockito.verify(itemRepository, Mockito.times(1)).findById(itemId);
        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(pedidoId);
        Mockito.verify(pedidoService, Mockito.times(1)).validate(pedido);
    }

    @Test
    void shouldUpdateItemPedido() {
        UUID id = itemPedido.getId();
        Mockito.when(itemPedidoRepository.findById(id)).thenReturn(Optional.of(itemPedido));
        Mockito.when(itemPedidoMapper.toEntity(itemPedidoForm)).thenReturn(itemPedido);
        Mockito.when(itemPedidoRepository.save(itemPedido)).thenReturn(itemPedido);
        Mockito.when(itemPedidoMapper.toDTO(itemPedido)).thenReturn(itemPedidoDTO);

        ItemPedidoDTO result = itemPedidoService.update(itemPedidoForm);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.id());
        Mockito.verify(itemPedidoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(itemPedidoRepository, Mockito.times(1)).save(itemPedido);
    }

    @Test
    void shouldDeleteItemPedido() {
        UUID id = itemPedido.getId();
        Mockito.when(itemPedidoRepository.findById(id)).thenReturn(Optional.of(itemPedido));

        itemPedidoService.delete(id);

        Mockito.verify(itemPedidoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(itemPedidoRepository, Mockito.times(1)).deleteById(id);
    }
}