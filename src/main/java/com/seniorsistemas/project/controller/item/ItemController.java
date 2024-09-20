package com.seniorsistemas.project.controller.item;

import java.util.UUID;

import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Item", description = "Operações relacionadas a itens")
public interface ItemController {

    @Operation(
            summary = "Listar todos os itens",
            description = "Lista todos os itens com a opção de filtrar por descrição e tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro interno no servidor")
    })
    ResponseEntity<Page<ItemDTO>> findAll(
            @Parameter(description = "Descrição do item") String descricao,
            @Parameter(description = "Tipo do item") TipoItem tipo,
            @Parameter(description = "Informações de paginação") Pageable pageable
    );

    @Operation(
            summary = "Buscar um item",
            description = "Busca um item específico pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<ItemDTO> findById(
            @Parameter(description = "Id do item") UUID id
    );

    @Operation(
            summary = "Cria um novo item",
            description = "Criar um novo item com as informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação. Verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<ItemDTO> create(
            @Parameter(description = "Objeto que contém as informações do item a ser criado") ItemForm itemForm
    );

    @Operation(
            summary = "Atualiza um item existente",
            description = "Atualiza um item existente utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação. Verifique os dados enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<ItemDTO> update(
            @Parameter(description = "Id do item") UUID id,
            @Parameter(description = "Objeto que contém as informações do item a ser atualizado") ItemForm itemForm
    );

    @Operation(
            summary = "Exclui um item",
            description = "Exclui um item utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Id do item") UUID id
    );

    @Operation(
            summary = "Inativa um item",
            description = "Inativa um item utilizando seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> inactivate(
            @Parameter(description = "Id do item") UUID id
    );
}
