package com.seniorsistemas.project.domain.pedido.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import com.seniorsistemas.project.domain.pedido.mapper.PedidoMapper;
import com.seniorsistemas.project.domain.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Page<PedidoDTO> findAll(Pageable pageable) {
        Page<Pedido> pedidoPage = pedidoRepository.findAll(pageable);
        return pedidoPage.map(this::calcularValores);
    }

    @Override
    public PedidoDTO findById(UUID id) {
        validateNotFound(id);

        Pedido pedido = pedidoRepository.findById(id).get();
        return calcularValores(pedido);
    }

    @Override
    public PedidoDTO save(PedidoForm pedidoForm) {
        Pedido pedido = PedidoMapper.MAPPER.toEntity(pedidoForm);
        pedido.setAtivo(true);
        return PedidoMapper.MAPPER.toDTO(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoDTO update(PedidoForm pedidoForm) throws Exception {
        validateNotFound(pedidoForm.getId());

        Pedido pedido = PedidoMapper.MAPPER.toEntity(pedidoForm);
        validate(pedido);
        return PedidoMapper.MAPPER.toDTO(pedidoRepository.save(pedido));
    }

    @Override
    public void delete(UUID id) {
        validateNotFound(id);
        pedidoRepository.deleteById(id);
    }

    @Override
    public void inactivate(UUID id) {
        validateNotFound(id);

        Pedido pedido = pedidoRepository.findById(id).get();
        pedido.setAtivo(false);
        pedidoRepository.save(pedido);
    }

    @Override
    public void close(UUID id) {
        validateNotFound(id);

        Pedido pedido = pedidoRepository.findById(id).get();
        pedido.setSituacao(SituacaoPedido.FECHADO);
        pedidoRepository.save(pedido);
    }

    @Override
    public void validate(Pedido pedido) throws Exception {
        if (pedido.getSituacao().equals(SituacaoPedido.FECHADO)) {
            throw new Exception("O pedido já foi fechado!");
        }
    }

    @Override
    public void validateNotFound(UUID id) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isEmpty()) {
            throw new NotFoundException(id);
        }
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
