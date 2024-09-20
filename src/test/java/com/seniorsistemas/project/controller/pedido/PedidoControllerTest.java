package com.seniorsistemas.project.controller.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.config.validation.exception.PedidoIsAlreadyClosedException;
import com.seniorsistemas.project.domain.pedido.dto.PedidoDTO;
import com.seniorsistemas.project.domain.pedido.entity.SituacaoPedido;
import com.seniorsistemas.project.domain.pedido.form.PedidoForm;
import com.seniorsistemas.project.domain.pedido.service.PedidoService;
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
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PedidoControllerImpl pedidoController;

    @MockBean
    private PedidoService pedidoService;

    private PedidoDTO pedidoDTO;
    private PedidoForm pedidoForm;

    private static final String PEDIDO_URL = "/api/pedido";
    private static final String PEDIDO_URL_WITH_ID = PEDIDO_URL + "/{id}";

    @BeforeEach
    void setUp() {
        pedidoDTO = new PedidoDTO(UUID.randomUUID(), LocalDateTime.now(), BigDecimal.valueOf(10), SituacaoPedido.ABERTO, true, BigDecimal.ZERO, BigDecimal.ZERO, null);
        pedidoForm = new PedidoForm(pedidoDTO.id(), pedidoDTO.data(), pedidoDTO.desconto(), pedidoDTO.situacao(), pedidoDTO.ativo());
    }

    @Test
    public void shouldFindAllPedidos() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PedidoDTO> pedidoPage = new PageImpl<>(List.of(pedidoDTO));
        Mockito.when(pedidoService.findAll(SituacaoPedido.ABERTO, pageable)).thenReturn(pedidoPage);

        mockMvc.perform(
                MockMvcRequestBuilders.get(PEDIDO_URL)
                        .param("situacao", SituacaoPedido.ABERTO.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(pedidoDTO.id().toString()));
    }

    @Test
    public void shouldFindPedidoById() throws Exception {
        UUID id = pedidoForm.getId();
        Mockito.when(pedidoService.findById(id)).thenReturn(pedidoDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.get(PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturnNotFoundStatusWhenItemNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.doThrow(new NotFoundException(id)).when(pedidoService).findById(id);

        mockMvc.perform(
                MockMvcRequestBuilders.get(PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestStatusWhenPedidoFormIsInvalid() throws Exception {
        pedidoForm.setData(null);
        pedidoForm.setDesconto(null);
        pedidoForm.setSituacao(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(PEDIDO_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldCreatePedido() throws Exception {
        Mockito.when(pedidoService.save(pedidoForm)).thenReturn(pedidoDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post(PEDIDO_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pedidoDTO.id().toString()));
    }

    @Test
    public void shouldUpdatePedido() throws Exception {
        UUID id = pedidoForm.getId();
        Mockito.when(pedidoService.update(pedidoForm)).thenReturn(pedidoDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put(PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pedidoDTO.id().toString()));
    }

    @Test
    public void shouldReturnBadRequestStatusWhenPedidoIsAlreadyClosed() throws Exception {
        UUID id = pedidoForm.getId();
        Mockito.when(pedidoService.update(pedidoForm)).thenThrow(new PedidoIsAlreadyClosedException());

        mockMvc.perform(
                MockMvcRequestBuilders.put(PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldDeletePedido() throws Exception {
        UUID id = pedidoForm.getId();
        Mockito.doNothing().when(pedidoService).delete(id);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(PEDIDO_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldInactivatePedido() throws Exception {
        UUID id = pedidoForm.getId();
        Mockito.doNothing().when(pedidoService).inactivate(id);

        mockMvc.perform(
                MockMvcRequestBuilders.put(PEDIDO_URL_WITH_ID + "/inactivate", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldClosePedido() throws Exception {
        UUID id = pedidoForm.getId();
        Mockito.doNothing().when(pedidoService).close(id);

        mockMvc.perform(
                MockMvcRequestBuilders.put(PEDIDO_URL_WITH_ID + "/close", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
