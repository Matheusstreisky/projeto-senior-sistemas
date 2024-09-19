package com.seniorsistemas.project.domain.pedido.mapper;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.Pedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {

    PedidoMapper MAPPER = Mappers.getMapper(PedidoMapper.class);

    PedidoDTO toDTO(Pedido pedido);

    Pedido toEntity(PedidoForm pedidoForm);
}
