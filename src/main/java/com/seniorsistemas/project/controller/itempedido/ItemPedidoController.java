package com.seniorsistemas.project.controller.itempedido;

import java.util.UUID;

import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Item do pedido", description = "Operações relacionadas a itens do pedido")
public interface ItemPedidoController {

    @Operation(
            summary = "Listar todos os itens de um pedido",
            description = "Lista todos os itens de um pedido utilizando seu Id do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens do pedido recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro interno no servidor")
    })
    ResponseEntity<Page<ItemPedidoDTO>> findByPedido(
            @Parameter(description = "Id do pedido") UUID pedidoId,
            @Parameter(description = "Informações de paginação") Pageable pageable
    );

    @Operation(
            summary = "Buscar um item do pedido",
            description = "Busca um item do pedido específico pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item do pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<ItemPedidoDTO> findById(
            @Parameter(description = "Id do item do pedido") UUID id
    );

    @Operation(
            summary = "Cria um novo item do pedido",
            description = "Criar um novo item do pedido com as informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item do pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação. Verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<ItemPedidoDTO> create(
            @Parameter(description = "Objeto que contém as informações do item do pedido a ser criado") ItemPedidoForm itemPedidoForm
    );

    @Operation(
            summary = "Atualiza um item do pedido existente",
            description = "Atualiza um item do pedido existente utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação. Verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<ItemPedidoDTO> update(
            @Parameter(description = "Id do item do pedido") UUID id,
            @Parameter(description = "Objeto que contém as informações do item do pedido a ser atualizado") ItemPedidoForm itemPedidoForm
    );

    @Operation(
            summary = "Exclui um item do pedido",
            description = "Exclui um item do pedido utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item do pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item do pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Id do item do pedido") UUID id
    );
}
