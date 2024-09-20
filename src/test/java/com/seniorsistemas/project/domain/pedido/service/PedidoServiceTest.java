package com.seniorsistemas.project.domain.pedido.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.config.validation.exception.PedidoIsAlreadyClosedException;
import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import com.seniorsistemas.project.domain.pedido.mapper.PedidoMapper;
import com.seniorsistemas.project.domain.pedido.repository.PedidoRepository;
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
class PedidoServiceTest {

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private PedidoForm pedidoForm;
    private Item itemProduto;
    private Item itemServico;
    private ItemDTO itemProdutoDTO;
    private ItemDTO itemServicoDTO;
    private ItemPedido itemPedidoProduto;
    private ItemPedido itemPedidoServico;
    private ItemPedidoDTO itemPedidoProdutoDTO;
    private ItemPedidoDTO itemPedidoServicoDTO;

    @BeforeEach
    void setup() {
        pedido = new Pedido(UUID.randomUUID(), LocalDateTime.now(), BigDecimal.valueOf(10), null, SituacaoPedido.ABERTO, true);
        pedidoForm = new PedidoForm(pedido.getId(), pedido.getData(), pedido.getDesconto(), pedido.getSituacao(), pedido.isAtivo());

        itemProduto = new Item(UUID.randomUUID(), "Produto Teste", TipoItem.PRODUTO, BigDecimal.valueOf(100), true);
        itemServico = new Item(UUID.randomUUID(), "Serviço Teste", TipoItem.SERVICO, BigDecimal.valueOf(200), true);
        itemProdutoDTO = new ItemDTO(itemProduto.getId(), itemProduto.getDescricao(), itemProduto.getTipo(), itemProduto.getValor(), itemProduto.isAtivo());
        itemServicoDTO = new ItemDTO(itemServico.getId(), itemServico.getDescricao(), itemServico.getTipo(), itemServico.getValor(), itemServico.isAtivo());

        itemPedidoProduto = new ItemPedido(UUID.randomUUID(), pedido, itemProduto, 2);
        itemPedidoServico = new ItemPedido(UUID.randomUUID(), pedido, itemServico, 2);
        itemPedidoProdutoDTO = new ItemPedidoDTO(itemPedidoProduto.getId(), itemProdutoDTO, itemPedidoProduto.getQuantidade());
        itemPedidoServicoDTO = new ItemPedidoDTO(itemPedidoServico.getId(), itemServicoDTO, itemPedidoServico.getQuantidade());

        pedido.setItens(List.of(itemPedidoProduto, itemPedidoServico));
        pedidoDTO = new PedidoDTO(pedido.getId(), pedido.getData(), pedido.getDesconto(), pedido.getSituacao(), pedido.isAtivo(),
                BigDecimal.ZERO, BigDecimal.ZERO, List.of(itemPedidoProdutoDTO, itemPedidoServicoDTO));
    }

    @Test
    void shouldFindAllPedidos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pedido> pedidoPage = new PageImpl<>(List.of(pedido), pageable, 1);
        Mockito.when(pedidoRepository.findAll(SituacaoPedido.ABERTO, pageable)).thenReturn(pedidoPage);
        Mockito.when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        Page<PedidoDTO> result = pedidoService.findAll(SituacaoPedido.ABERTO, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(pedidoDTO.id(), result.getContent().get(0).id());
        Assertions.assertEquals(BigDecimal.valueOf(600).setScale(2, RoundingMode.HALF_UP), result.getContent().get(0).valorTotal());
        Assertions.assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), result.getContent().get(0).valorDesconto());
        Mockito.verify(pedidoRepository, Mockito.times(1)).findAll(SituacaoPedido.ABERTO, pageable);
    }

    @Test
    void shouldFindPedidoById() {
        UUID id = pedido.getId();
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        Mockito.when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = pedidoService.findById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.id());
        Assertions.assertEquals(BigDecimal.valueOf(600).setScale(2, RoundingMode.HALF_UP), result.valorTotal());
        Assertions.assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), result.valorDesconto());
        Mockito.verify(pedidoRepository, Mockito.times(2)).findById(id);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPedidoNotFound() {
        UUID id = pedido.getId();
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> pedidoService.findById(id));

        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void shouldSaveNewPedido() {
        Mockito.when(pedidoMapper.toEntity(pedidoForm)).thenReturn(pedido);
        Mockito.when(pedidoRepository.save(pedido)).thenReturn(pedido);
        Mockito.when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = pedidoService.save(pedidoForm);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(pedido.getId(), result.id());
        Assertions.assertTrue(pedido.isAtivo());
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedido);
    }

    @Test
    void shouldUpdatePedido() {
        UUID id = pedido.getId();
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        Mockito.when(pedidoMapper.toEntity(pedidoForm)).thenReturn(pedido);
        Mockito.when(pedidoRepository.save(pedido)).thenReturn(pedido);
        Mockito.when(pedidoMapper.toDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO result = pedidoService.update(pedidoForm);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.id());
        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedido);
    }

    @Test
    void shouldThrowPedidoIsAlreadyClosedExceptionWhenPedidoIsAlreadyClosed() {
        UUID id = pedido.getId();
        pedido.setSituacao(SituacaoPedido.FECHADO);
        pedidoForm.setSituacao(SituacaoPedido.FECHADO);

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        Mockito.when(pedidoMapper.toEntity(pedidoForm)).thenReturn(pedido);

        Assertions.assertThrows(PedidoIsAlreadyClosedException.class, () -> pedidoService.update(pedidoForm));

        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void shouldDeletePedido() {
        UUID id = pedido.getId();
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        pedidoService.delete(id);

        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(id);
        Mockito.verify(pedidoRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void shouldInactivatePedido() {
        UUID id = pedido.getId();
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        pedidoService.inactivate(id);

        Assertions.assertFalse(pedido.isAtivo());
        Mockito.verify(pedidoRepository, Mockito.times(2)).findById(id);
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedido);
    }

    @Test
    void shouldClosePedido() {
        UUID id = pedido.getId();
        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        pedidoService.close(id);

        Assertions.assertEquals(SituacaoPedido.FECHADO, pedido.getSituacao());
        Mockito.verify(pedidoRepository, Mockito.times(2)).findById(id);
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedido);
    }
}