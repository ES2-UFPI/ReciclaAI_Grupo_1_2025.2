package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.service.EventoBeneficiamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoBeneficiamentoController.class)
class EventoBeneficiamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoBeneficiamentoService eventoBeneficiamentoService;

    @Test
    @DisplayName("Deve retornar lista de eventos quando existirem eventos no bairro")
    void testListarEventosPorBairro_ComDados() throws Exception {
        // Arrange
        EventoBeneficiamentoDTO dto1 = new EventoBeneficiamentoDTO();
        dto1.setId(1L);
        dto1.setStatus(StatusBeneficiamentoEnum.AGENDADA);

        EventoBeneficiamentoDTO dto2 = new EventoBeneficiamentoDTO();
        dto2.setId(2L);
        dto2.setStatus(StatusBeneficiamentoEnum.CONCLUIDA);

        when(eventoBeneficiamentoService.findByBairro("Centro")).thenReturn(Arrays.asList(dto1, dto2));

        // Act & Assert
        mockMvc.perform(get("/eventos-beneficiamento/bairro/Centro")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(eventoBeneficiamentoService, times(1)).findByBairro("Centro");
    }

    @Test
    @DisplayName("Deve retornar mensagem quando não existirem eventos no bairro")
    void testListarEventosPorBairro_SemDados() throws Exception {
        // Arrange
        when(eventoBeneficiamentoService.findByBairro("BairroInexistente"))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/eventos-beneficiamento/bairro/BairroInexistente")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem").value("Não existem eventos de beneficiamento no bairro: BairroInexistente"));

        verify(eventoBeneficiamentoService, times(1)).findByBairro("BairroInexistente");
    }

    @Test
    @DisplayName("Deve chamar o service com o bairro correto")
    void testListarEventosPorBairro_ChamadaCorreta() throws Exception {
        // Arrange
        when(eventoBeneficiamentoService.findByBairro(anyString())).thenReturn(Collections.emptyList());

        // Act
        mockMvc.perform(get("/eventos-beneficiamento/bairro/Teste")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        verify(eventoBeneficiamentoService, times(1)).findByBairro("Teste");
    }
}
