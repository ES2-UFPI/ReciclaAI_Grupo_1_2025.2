package br.ufpi.recicle_ai.controller.eventoColeta;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.EventoColetaDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import br.ufpi.recicle_ai.service.EventoColetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoColetaController.class)
@DisplayName("Testes do EventoColetaController - Endpoint GET /eventos-coleta/coletor/{id}")
class EventoColetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventoColetaService eventoColetaService;

    @Test
    @DisplayName("Deve retornar lista de eventos quando coletor possui eventos cadastrados")
    void deveBuscarEventosPorColetorIdComSucesso() throws Exception {
        // Arrange
        Long coletorId = 1L;
        EventoColetaDTO evento1 = new EventoColetaDTO();
        evento1.setId(1L);
        evento1.setStatus(StatusEventoColetaEnum.AGENDADA);

        EventoColetaDTO evento2 = new EventoColetaDTO();
        evento2.setId(2L);
        evento2.setStatus(StatusEventoColetaEnum.CONCLUIDA);

        List<EventoColetaDTO> eventosEsperados = Arrays.asList(evento1, evento2);

        when(eventoColetaService.findAllByColetorId(coletorId)).thenReturn(eventosEsperados);

        // Act & Assert
        mockMvc.perform(get("/eventos-coleta/coletor/{id}", coletorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("AGENDADA"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].status").value("CONCLUIDA"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando coletor não possui eventos")
    void deveRetornarListaVaziaQuandoColetorNaoPossuiEventos() throws Exception {
        // Arrange
        Long coletorId = 999L;
        when(eventoColetaService.findAllByColetorId(coletorId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/eventos-coleta/coletor/{id}", coletorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Deve retornar 200 OK mesmo com ID de coletor inválido (lista vazia)")
    void deveRetornarOkComIdInvalido() throws Exception {
        // Arrange
        Long coletorIdInvalido = -1L;
        when(eventoColetaService.findAllByColetorId(anyLong())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/eventos-coleta/coletor/{id}", coletorIdInvalido)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
