package com.seniorsistemas.project.domain.itempedido.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.entity.ItemPedido;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.itempedido.mapper.ItemPedidoMapper;
import com.seniorsistemas.project.domain.itempedido.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ItemPedidoDTO> findByPedido(UUID pedidoId) {
        return ItemPedidoMapper.MAPPER.toDTOList(itemPedidoRepository.findByPedido_Id(pedidoId));
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
