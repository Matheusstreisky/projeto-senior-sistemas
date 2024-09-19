package com.seniorsistemas.project.domain.itempedido.service;

import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.itempedido.mapper.ItemPedidoMapper;
import com.seniorsistemas.project.domain.itempedido.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

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
    public ItemPedidoDTO save(ItemPedidoForm itemPedidoForm) {
        ItemPedido itemPedido = ItemPedidoMapper.MAPPER.toEntity(itemPedidoForm);
        return ItemPedidoMapper.MAPPER.toDTO(itemPedidoRepository.save(itemPedido));
    }

    @Override
    public void delete(UUID id) {
        itemPedidoRepository.deleteById(id);
    }
}
