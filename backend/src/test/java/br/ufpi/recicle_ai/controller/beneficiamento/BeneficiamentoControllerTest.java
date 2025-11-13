package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.service.BeneficiamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficiamentoController.class)
class BeneficiamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficiamentoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarListaDeBeneficiamentosPorReceptorId() throws Exception {
        // Simula o DTO retornado conforme o exemplo do Swagger
        BeneficiamentoDTO beneficiamento = new BeneficiamentoDTO();
        beneficiamento.setId(1L);
        beneficiamento.setDataInicio(LocalDateTime.parse("2024-08-01T09:00:00"));
        beneficiamento.setDataFim(LocalDateTime.parse("2024-08-01T17:00:00"));
        // (os demais campos podem ser adicionados conforme a estrutura do DTO real)

        Mockito.when(service.findEntityByReceptorId(anyLong()))
                .thenReturn(List.of(beneficiamento));

        // Execução e verificação
        mockMvc.perform(get("/beneficiamentos/receptor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // valida o tamanho do array
                .andExpect(jsonPath("$", hasSize(1)))
                // valida alguns campos conforme o Swagger
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].dataInicio").value("2024-08-01T09:00:00"))
                .andExpect(jsonPath("$[0].dataFim").value("2024-08-01T17:00:00"));
    }

    @Test
    void deveRetornarBeneficiamentoPorId() throws Exception {
        BeneficiamentoDTO dto = new BeneficiamentoDTO();
        dto.setId(4L);
        dto.setDataInicio(LocalDateTime.parse("2024-08-01T09:00:00"));
        dto.setDataFim(LocalDateTime.parse("2024-08-01T17:00:00"));

        Mockito.when(service.findById(4L)).thenReturn(dto);

        mockMvc.perform(get("/beneficiamentos/4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.dataInicio").value("2024-08-01T09:00:00"))
                .andExpect(jsonPath("$.dataFim").value("2024-08-01T17:00:00"));
    }
}

