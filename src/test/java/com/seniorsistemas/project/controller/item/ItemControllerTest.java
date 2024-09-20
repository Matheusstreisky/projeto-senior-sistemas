package com.seniorsistemas.project.controller.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seniorsistemas.project.config.validation.exception.ItemIsBeingUsedInPedidoException;
import com.seniorsistemas.project.config.validation.exception.NotFoundException;
import com.seniorsistemas.project.domain.item.dto.ItemDTO;
import com.seniorsistemas.project.domain.item.entity.TipoItem;
import com.seniorsistemas.project.domain.item.form.ItemForm;
import com.seniorsistemas.project.domain.item.service.ItemService;
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
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemControllerImpl itemController;

    @MockBean
    private ItemService itemService;

    private ItemDTO itemDTO;
    private ItemForm itemForm;

    private static final String ITEM_URL = "/api/item";
    private static final String ITEM_URL_WITH_ID = ITEM_URL + "/{id}";

    @BeforeEach
    void setUp() {
        itemDTO = new ItemDTO(UUID.randomUUID(), "Item Teste", TipoItem.PRODUTO, BigDecimal.valueOf(100), true);
        itemForm = new ItemForm(itemDTO.id(), itemDTO.descricao(), itemDTO.tipo(), itemDTO.valor(), itemDTO.ativo());
    }

    @Test
    void shouldFindAllItems() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ItemDTO> itemPage = new PageImpl<>(List.of(itemDTO));
        Mockito.when(itemService.findAll(null, null, pageable)).thenReturn(itemPage);

        mockMvc.perform(
                MockMvcRequestBuilders.get(ITEM_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(itemDTO.id().toString()));
    }

    @Test
    void shouldFindItemById() throws Exception {
        UUID id = itemForm.getId();
        Mockito.when(itemService.findById(id)).thenReturn(itemDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.get(ITEM_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturnNotFoundStatusWhenItemNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.doThrow(new NotFoundException(id)).when(itemService).findById(id);

        mockMvc.perform(
                MockMvcRequestBuilders.get(ITEM_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestStatusWhenItemFormIsInvalid() throws Exception {
        itemForm.setDescricao("");
        itemForm.setTipo(null);
        itemForm.setValor(null);

        mockMvc.perform(
                MockMvcRequestBuilders.post(ITEM_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldCreateItem() throws Exception {
        Mockito.when(itemService.save(itemForm)).thenReturn(itemDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post(ITEM_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(itemDTO.id().toString()));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        UUID id = itemForm.getId();
        Mockito.when(itemService.update(itemForm)).thenReturn(itemDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put(ITEM_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        UUID id = itemForm.getId();
        Mockito.doNothing().when(itemService).delete(id);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(ITEM_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestStatusWhenItemIsBeingUsedInPedido() throws Exception {
        UUID id = itemForm.getId();
        Mockito.doThrow(new ItemIsBeingUsedInPedidoException()).when(itemService).delete(id);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(ITEM_URL_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldInactivateItem() throws Exception {
        UUID id = itemForm.getId();
        Mockito.doNothing().when(itemService).inactivate(id);

        mockMvc.perform(
                MockMvcRequestBuilders.put(ITEM_URL_WITH_ID + "/inactivate", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
