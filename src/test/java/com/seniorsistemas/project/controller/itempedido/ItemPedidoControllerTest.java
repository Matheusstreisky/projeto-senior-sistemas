package com.seniorsistemas.project.controller.itempedido;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seniorsistemas.project.config.validation.exception.ItemIsInactiveException;
import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.config.validation.exception.PedidoIsAlreadyClosedException;
import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.itempedido.dto.ItemPedidoDTO;
import com.seniorsistemas.project.domain.itempedido.form.ItemPedidoForm;
import com.seniorsistemas.project.domain.itempedido.service.ItemPedidoService;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ItemPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemPedidoControllerImpl itemPedidoController;

    @MockBean
    private ItemPedidoService itemPedidoService;

    private ItemPedidoDTO itemPedidoDTO;
    private ItemPedidoForm itemPedidoForm;
    private ItemDTO itemDTO;
    private PedidoDTO pedidoDTO;

    private static final String ITEM_PEDIDO_URL = "/api/itempedido";
    private static final String ITEM_PEDIDO_URL_WITH_ID = ITEM_PEDIDO_URL + "/{id}";

    @BeforeEach
    public void setUp() {
        itemDTO = new ItemDTO(UUID.randomUUID(), null, null, null, true);
        pedidoDTO = new PedidoDTO(UUID.randomUUID(), null, null, null, true, null, null, null);

        itemPedidoDTO = new ItemPedidoDTO(UUID.randomUUID(), itemDTO, 1);
        itemPedidoForm = new ItemPedidoForm(itemPedidoDTO.id(), pedidoDTO, itemPedidoDTO.item(), itemPedidoDTO.quantidade());
    }

    @Test
    public void shouldReturnItemPedidoPageWhenFindByPedido() throws Exception {
        UUID pedidoId = pedidoDTO.id();
        Pageable pageable = PageRequest.of(0, 10);
        Page<ItemPedidoDTO> itemPedidoPage = new PageImpl<>(List.of(itemPedidoDTO));

        Mockito.when(itemPedidoService.findByPedido(pedidoId, pageable)).thenReturn(itemPedidoPage);

        mockMvc.perform(
                MockMvcRequestBuilders.get(ITEM_PEDIDO_URL + "/pedido/{pedidoId}", pedidoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(itemPedidoDTO.id().toString()));
    }

    @Test
    public void shouldReturnItemPedidoById() throws Exception {
        UUID id = itemPedidoForm.getId();
        Mockito.when(itemPedidoService.findById(id)).thenReturn(itemPedidoDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.get(ITEM_PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }

    @Test
    public void shouldReturnNotFoundStatusWhenItemPedidoDoesNotExist() throws Exception {
        UUID id = itemPedidoForm.getId();
        Mockito.when(itemPedidoService.findById(id)).thenThrow(new NotFoundException(id));

        mockMvc.perform(
                MockMvcRequestBuilders.get(ITEM_PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldCreateItemPedido() throws Exception {
        Mockito.when(itemPedidoService.save(itemPedidoForm)).thenReturn(itemPedidoDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post(ITEM_PEDIDO_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(itemPedidoDTO.id().toString()));
    }

    @Test
    public void shouldReturnBadRequestStatusWhenItemIsInactive() throws Exception {
        Mockito.when(itemPedidoService.save(itemPedidoForm)).thenThrow(new ItemIsInactiveException());

        mockMvc.perform(
                MockMvcRequestBuilders.post(ITEM_PEDIDO_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestStatusWhenPedidoIsAlreadyClosed() throws Exception {
        Mockito.when(itemPedidoService.save(itemPedidoForm)).thenThrow(new PedidoIsAlreadyClosedException());

        mockMvc.perform(
                MockMvcRequestBuilders.post(ITEM_PEDIDO_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldUpdateItemPedido() throws Exception {
        UUID id = itemPedidoForm.getId();
        Mockito.when(itemPedidoService.update(itemPedidoForm)).thenReturn(itemPedidoDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put(ITEM_PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }

    @Test
    public void shouldDeleteItemPedido() throws Exception {
        UUID id = itemPedidoForm.getId();
        Mockito.doNothing().when(itemPedidoService).delete(id);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(ITEM_PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
