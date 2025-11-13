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
import static org.mockito.ArgumentMatchers.anyString;
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
        BeneficiamentoDTO beneficiamento = new BeneficiamentoDTO();
        Page<BeneficiamentoDTO> page = new PageImpl<>(List.of(beneficiamento));
        Mockito.when(service.findByReceptor(anyLong(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/beneficiamentos/receptor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Deve retornar lista paginada de beneficiamentos por Bairro")
    void deveRetornarListaPaginadaDeBeneficiamentosPorBairro() throws Exception {
        BeneficiamentoDTO beneficiamento = new BeneficiamentoDTO();
        Page<BeneficiamentoDTO> page = new PageImpl<>(List.of(beneficiamento));
        Mockito.when(service.findByBairro(anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/beneficiamentos/por-bairro")
                        .param("bairro", "Centro")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void deveRetornarBeneficiamentoPorId() throws Exception {
        BeneficiamentoDTO dto = new BeneficiamentoDTO();
        dto.setId(4L);
        Mockito.when(service.findById(4L)).thenReturn(dto);

        mockMvc.perform(get("/beneficiamentos/4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }
}
