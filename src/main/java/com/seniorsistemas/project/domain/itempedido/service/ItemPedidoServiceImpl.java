package com.seniorsistemas.project.domain.itempedido.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.Item;
import com.seniorsistemas.project.domain.item.repository.ItemRepository;
import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.itempedido.mapper.ItemPedidoMapper;
import com.seniorsistemas.project.domain.itempedido.repository.ItemPedidoRepository;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.repository.PedidoRepository;
import com.seniorsistemas.project.domain.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Override
    public Optional<ItemPedidoDTO> findById(UUID id) {
        return ItemPedidoMapper.MAPPER.toOptionalItemDTO(itemPedidoRepository.findById(id));
    }

    @Override
    public Page<ItemPedidoDTO> findByPedido(UUID pedidoId, Pageable pageable) {
        Page<ItemPedido> itemPedidoPage = itemPedidoRepository.findByPedido_Id(pedidoId, pageable);
        return itemPedidoPage.map(ItemPedidoMapper.MAPPER::toDTO);
    }

    @Override
    public ItemPedidoDTO save(ItemPedidoForm itemPedidoForm) throws Exception {
        ItemPedido itemPedido = ItemPedidoMapper.MAPPER.toEntity(itemPedidoForm);
        validate(itemPedido);
        return ItemPedidoMapper.MAPPER.toDTO(itemPedidoRepository.save(itemPedido));
    }

    @Override
    public void validate(ItemPedido itemPedido) throws Exception {
        Optional<Item> optionalItem = itemRepository.findById(itemPedido.getItem().getId());
        if (optionalItem.isPresent() && !optionalItem.get().isAtivo()) {
            throw new Exception("O item está inativo!");
        }

        Optional<Pedido> optionalPedido = pedidoRepository.findById(itemPedido.getPedido().getId());
        if (optionalItem.isPresent()) {
            pedidoService.validate(optionalPedido.get());
        }
    }

    @Override
    public void delete(UUID id) {
        itemPedidoRepository.deleteById(id);
    }
}
