package br.ufpi.recicle_ai.controller.coleta;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.service.ColetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes para a classe {@link ColetaController}.
 * Cobre todos os endpoints e garante a interação correta com o serviço.
 */
@WebMvcTest(ColetaController.class)
class ColetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColetaService coletaService;

    private Page<ColetaDTO> mockPage;
    private ColetaDTO coletaDTO;

    @BeforeEach
    void setup() {
        coletaDTO = new ColetaDTO(); // supondo que tenha getters e setters
        mockPage = new PageImpl<>(List.of(coletaDTO));
    }

    @Test
    @DisplayName("GET /coletas - deve retornar todas as coletas com sucesso")
    void testFindAll() throws Exception {
        when(coletaService.findAll(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/coletas")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray());

        verify(coletaService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas/por-bairro?bairro=Centro - deve retornar coletas filtradas por bairro")
    void testFindByBairro() throws Exception {
        when(coletaService.findByBairro(eq("Centro"), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/coletas/por-bairro")
                        .param("bairro", "Centro")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray());

        verify(coletaService, times(1)).findByBairro(eq("Centro"), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas/por-bairro sem parâmetro - deve retornar erro 400")
    void testFindByBairroMissingParam() throws Exception {
        mockMvc.perform(get("/coletas/por-bairro")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(coletaService, never()).findByBairro(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas/coletor/{id} - deve retornar coletas filtradas por coletor")
    void testFindByColetor() throws Exception {
        when(coletaService.findByColetor(eq(1L), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/coletas/coletor/{id}", 1L)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray());

        verify(coletaService, times(1)).findByColetor(eq(1L), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas/coletor/{id} com id inválido (não numérico) - deve retornar erro 400")
    void testFindByColetorInvalidId() throws Exception {
        mockMvc.perform(get("/coletas/coletor/abc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(coletaService, never()).findByColetor(anyLong(), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas - deve lidar com serviço retornando página vazia")
    void testFindAllEmpty() throws Exception {
        when(coletaService.findAll(any(Pageable.class))).thenReturn(Page.empty(PageRequest.of(0, 10)));

        mockMvc.perform(get("/coletas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());

        verify(coletaService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas/por-bairro - deve propagar exceção do serviço como erro 500")
    void testFindByBairroThrowsException() throws Exception {
        when(coletaService.findByBairro(eq("Centro"), any(Pageable.class)))
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/coletas/por-bairro")
                        .param("bairro", "Centro")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(coletaService, times(1)).findByBairro(eq("Centro"), any(Pageable.class));
    }
}

