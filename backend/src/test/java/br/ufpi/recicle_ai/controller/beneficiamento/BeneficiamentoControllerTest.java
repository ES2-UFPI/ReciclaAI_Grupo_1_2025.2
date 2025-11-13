package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.service.BeneficiamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("Deve retornar lista paginada de beneficiamentos por ID do receptor")
    void deveRetornarListaPaginadaDeBeneficiamentosPorReceptorId() throws Exception {
        // Arrange
        BeneficiamentoDTO beneficiamento = new BeneficiamentoDTO();
        beneficiamento.setId(1L);
        beneficiamento.setDataInicio(LocalDateTime.parse("2024-08-01T09:00:00"));
        beneficiamento.setDataFim(LocalDateTime.parse("2024-08-01T17:00:00"));

        Page<BeneficiamentoDTO> page = new PageImpl<>(List.of(beneficiamento), PageRequest.of(0, 10), 1);

        Mockito.when(service.findByReceptor(anyLong(), any(Pageable.class)))
                .thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/beneficiamentos/receptor/1")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Valida a estrutura da página
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.number").value(0))
                // Valida os campos do primeiro elemento
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].dataInicio").value("2024-08-01T09:00:00"))
                .andExpect(jsonPath("$.content[0].dataFim").value("2024-08-01T17:00:00"));
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
