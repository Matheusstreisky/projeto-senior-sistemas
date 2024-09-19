package com.seniorsistemas.project.domain.pedido.mapper;

import java.util.List;
import java.util.Optional;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {

    PedidoMapper MAPPER = Mappers.getMapper(PedidoMapper.class);

    PedidoDTO toDTO(Pedido pedido);

    Pedido toEntity(PedidoDTO pedidoDTO);

    Pedido toEntity(PedidoForm pedidoForm);

    List<PedidoDTO> toDTOList(List<Pedido> pedidos);

    default Optional<PedidoDTO> toOptionalItemDTO(Optional<Pedido> pedido) {
        return pedido.map(this::toDTO);
    }
}
