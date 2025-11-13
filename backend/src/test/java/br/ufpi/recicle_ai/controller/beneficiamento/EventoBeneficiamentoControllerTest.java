package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.EventoBeneficiamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoBeneficiamentoController.class)
public class EventoBeneficiamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoBeneficiamentoService eventoBeneficiamentoService;

    @Test
    @DisplayName("POST /eventos-beneficiamento - cria evento com sucesso e retorna 201")
    void createEvento_success_returnsCreatedAndBody() throws Exception {
        // Arrange
        ColetorDTO coletor = new ColetorDTO();
        coletor.setId(5L);
        coletor.setNome("Coletor Teste");

        BeneficiamentoDTO beneficiamento = new BeneficiamentoDTO();
        beneficiamento.setId(10L);

        EventoBeneficiamentoDTO retorno = new EventoBeneficiamentoDTO();
        retorno.setId(1L);
        retorno.setColetor(coletor);
        retorno.setBeneficiamento(beneficiamento);

        Mockito.when(eventoBeneficiamentoService.create(any(EventoBeneficiamentoForm.class))).thenReturn(retorno);

        String requestJson = "{\n" +
                "  \"beneficiamentoId\": 10,\n" +
                "  \"coletorId\": 5\n" +
                "}";

        // Act & Assert
        mockMvc.perform(post("/eventos-beneficiamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/eventos-beneficiamento/1")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.beneficiamento.id").value(10))
                .andExpect(jsonPath("$.coletor.id").value(5))
                .andExpect(jsonPath("$.itens").isArray())
                .andExpect(jsonPath("$.itens").isEmpty());

        verify(eventoBeneficiamentoService, times(1)).create(any(EventoBeneficiamentoForm.class));
    }

    @Test
    @DisplayName("POST /eventos-beneficiamento - falha de validação quando beneficiamentoId ausente retorna 400")
    void createEvento_missingBeneficiamentoId_returnsBadRequest() throws Exception {
        String requestJson = "{\n" +
                "  \"coletorId\": 5\n" +
                "}";

        mockMvc.perform(post("/eventos-beneficiamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(eventoBeneficiamentoService, never()).create(any(EventoBeneficiamentoForm.class));
    }

    @Test
    @DisplayName("POST /eventos-beneficiamento - quando o serviço lança exceção, mapeia para erro interno")
    void createEvento_serviceThrowsException_throwsServletException() throws Exception {
        Mockito.when(eventoBeneficiamentoService.create(any(EventoBeneficiamentoForm.class)))
                .thenThrow(new RuntimeException("Beneficiamento não encontrado"));

        String requestJson = "{\n" +
                "  \"beneficiamentoId\": 999,\n" +
                "  \"coletorId\": 5\n" +
                "}";

        org.junit.jupiter.api.Assertions.assertThrows(
                jakarta.servlet.ServletException.class,
                () -> mockMvc.perform(post("/eventos-beneficiamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andReturn()
        );

        verify(eventoBeneficiamentoService, times(1)).create(any(EventoBeneficiamentoForm.class));
    }
}
