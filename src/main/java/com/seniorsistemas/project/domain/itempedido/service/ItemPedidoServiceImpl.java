package com.seniorsistemas.project.domain.itempedido.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.config.validation.exception.ItemIsInactiveException;
import com.seniorsistemas.project.config.validation.exception.NotFoundException;
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
    public Page<ItemPedidoDTO> findByPedido(UUID pedidoId, Pageable pageable) {
        Page<ItemPedido> itemPedidoPage = itemPedidoRepository.findByPedido_Id(pedidoId, pageable);
        return itemPedidoPage.map(ItemPedidoMapper.MAPPER::toDTO);
    }

    @Override
    public ItemPedidoDTO findById(UUID id) {
        validateNotFound(id);
        return ItemPedidoMapper.MAPPER.toDTO(itemPedidoRepository.findById(id).get());
    }

    @Override
    public ItemPedidoDTO save(ItemPedidoForm itemPedidoForm) {
        ItemPedido itemPedido = ItemPedidoMapper.MAPPER.toEntity(itemPedidoForm);
        validate(itemPedido);
        return ItemPedidoMapper.MAPPER.toDTO(itemPedidoRepository.save(itemPedido));
    }

    @Override
    public ItemPedidoDTO update(ItemPedidoForm itemPedidoForm) {
        validateNotFound(itemPedidoForm.getId());
        return save(itemPedidoForm);
    }

    @Override
    public void delete(UUID id) {
        validateNotFound(id);
        itemPedidoRepository.deleteById(id);
    }

    @Override
    public void validate(ItemPedido itemPedido) {
        Optional<Item> optionalItem = itemRepository.findById(itemPedido.getItem().getId());
        if (optionalItem.isPresent() && !optionalItem.get().isAtivo()) {
            throw new ItemIsInactiveException();
        }

        Optional<Pedido> optionalPedido = pedidoRepository.findById(itemPedido.getPedido().getId());
        if (optionalItem.isPresent()) {
            pedidoService.validate(optionalPedido.get());
        }
    }

    @Override
    public void validateNotFound(UUID id) {
        Optional<ItemPedido> optionalItemPedido = itemPedidoRepository.findById(id);
        if (optionalItemPedido.isEmpty()) {
            throw new NotFoundException(id);
        }
    }
}
