package br.ufpi.recicle_ai.controller.beneficiamento;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.service.EventoBeneficiamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoBeneficiamentoController.class)
class EventoBeneficiamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventoBeneficiamentoService eventoBeneficiamentoService;

    @Test
    void create_Success() throws Exception {
        EventoBeneficiamentoForm form = new EventoBeneficiamentoForm();
        form.setBeneficiamentoId(1L);
        form.setColetorId(1L);

        EventoBeneficiamentoDTO dto = new EventoBeneficiamentoDTO();
        dto.setId(1L);

        when(eventoBeneficiamentoService.create(any(EventoBeneficiamentoForm.class))).thenReturn(dto);

        mockMvc.perform(post("/eventos-beneficiamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void delete_Success() throws Exception {
        doNothing().when(eventoBeneficiamentoService).delete(anyLong());

        mockMvc.perform(delete("/eventos-beneficiamento/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAllByColetorId_Success() throws Exception {
        when(eventoBeneficiamentoService.findAllByColetorId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/eventos-beneficiamento/coletor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllByReceptorId_Success() throws Exception {
        when(eventoBeneficiamentoService.findAllByReceptorId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/eventos-beneficiamento/receptor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
