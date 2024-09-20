package com.seniorsistemas.project.controller.pedido;

import java.util.UUID;

import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedido", description = "Operações relacionadas a pedidos")
public interface PedidoController {

    @Operation(
            summary = "Listar todos os pedidos",
            description = "Lista todos os pedidos com a opção de filtrar por situacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro interno no servidor")
    })
    ResponseEntity<Page<PedidoDTO>> findAll(
            @Parameter(description = "Situação do pedido") SituacaoPedido situacao,
            @Parameter(description = "Informações de paginação") Pageable pageable
    );

    @Operation(
            summary = "Buscar um pedido",
            description = "Busca um pedido específico pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<PedidoDTO> findById(
            @Parameter(description = "Id do pedido") UUID id
    );

    @Operation(
            summary = "Cria um novo pedido",
            description = "Criar um novo pedido com as informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação. Verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<PedidoDTO> create(
            @Parameter(description = "Objeto que contém as informações do pedido a ser criado") PedidoForm pedidoForm
    );

    @Operation(
            summary = "Atualiza um pedido existente",
            description = "Atualiza um pedido existente utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação. Verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<PedidoDTO> update(
            @Parameter(description = "Id do pedido") UUID id,
            @Parameter(description = "Objeto que contém as informações do pedido a ser atualizado") PedidoForm pedidoForm
    );

    @Operation(
            summary = "Exclui um pedido",
            description = "Exclui um pedido utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Id do pedido") UUID id
    );

    @Operation(
            summary = "Inativa um pedido",
            description = "Inativa um pedido utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> inactivate(
            @Parameter(description = "Id do pedido") UUID id
    );

    @Operation(
            summary = "Fecha um pedido",
            description = "Altera a situação de um pedido para FECHADO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> close(
            @Parameter(description = "Id do pedido") UUID id
    );
}
