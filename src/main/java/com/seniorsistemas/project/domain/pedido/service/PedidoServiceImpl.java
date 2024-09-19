package com.seniorsistemas.project.domain.pedido.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import com.seniorsistemas.project.domain.pedido.mapper.PedidoMapper;
import com.seniorsistemas.project.domain.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<PedidoDTO> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(this::calcularValores)
                .toList();
    }

    @Override
    public Optional<PedidoDTO> findById(UUID id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (pedido.isPresent()) {
            PedidoDTO pedidoDTO = calcularValores(pedido.get());
            return Optional.of(pedidoDTO);
        }

        return Optional.empty();
    }

    @Override
    public PedidoDTO save(PedidoForm pedidoForm) {
        Pedido pedido = PedidoMapper.MAPPER.toEntity(pedidoForm);
        return PedidoMapper.MAPPER.toDTO(pedidoRepository.save(pedido));
    }

    @Override
    public void delete(UUID id) {
        pedidoRepository.deleteById(id);
    }

    private PedidoDTO calcularValores(Pedido pedido) {
        PedidoDTO pedidoDTO = PedidoMapper.MAPPER.toDTO(pedido);
        pedidoDTO = pedidoDTO.withValorTotal(getValorTotal(pedidoDTO));
        pedidoDTO = pedidoDTO.withValorDesconto(getValorDesconto(pedidoDTO));

        return pedidoDTO;
    }

    private BigDecimal getValorTotal(PedidoDTO pedidoDTO) {
        BigDecimal valorTotal = pedidoDTO.itens()
                .stream()
                .map(itemPedidoDTO -> itemPedidoDTO.item().valor().multiply(BigDecimal.valueOf(itemPedidoDTO.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return valorTotal.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getValorDesconto(PedidoDTO pedidoDTO) {
        BigDecimal valorTotalProdutos = pedidoDTO.itens()
                .stream()
                .filter(itemPedidoDTO -> itemPedidoDTO.item().tipo().equals(TipoItem.PRODUTO))
                .map(itemPedidoDTO -> itemPedidoDTO.item().valor().multiply(BigDecimal.valueOf(itemPedidoDTO.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return valorTotalProdutos
                .multiply(pedidoDTO.desconto())
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
