package br.ufpi.recicle_ai.controller.coleta;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.dto.coleta.PontoColetaDTO;
import br.ufpi.recicle_ai.domain.form.coleta.ColetaForm;
import br.ufpi.recicle_ai.service.ColetaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ColetaController.class)
public class ColetaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ColetaService coletaService;
    private Page<ColetaDTO> mockPage;

    @Test
    void createColetas_returnsCreatedAndBody() throws Exception {
        // Arrange: prepare expected DTO returned by service
        ColetorDTO coletor = new ColetorDTO();
        coletor.setId(2L);
        coletor.setNome("José de Freitas");
        coletor.setCpf("012.345.678-90");

        PontoColetaDTO ponto = PontoColetaDTO.builder()
                .id(1L)
                .logradouro("Rua Álvaro Mendes")
                .numero("74")
                .bairro("Centro")
                .cep("64000-000")
                .build();

        ColetaDTO retorno = new ColetaDTO();
        retorno.setId(1L);
        retorno.setColetor(coletor);
        retorno.setDataInicio(LocalDateTime.parse("2025-11-30T09:00:00"));
        retorno.setDataFim(LocalDateTime.parse("2025-11-30T12:00:00"));
        retorno.setPontoColeta(ponto);
        retorno.setItensColeta(Collections.emptyList());

        Mockito.when(coletaService.createColetas(any(ColetaForm.class))).thenReturn(retorno);

        // Request body as described in the issue (fields accepted by controller/service)
        String requestJson = "{\n" +
                "  \"coletorId\": 2,\n" +
                "  \"dataInicio\": \"2025-11-30T09:00:00\",\n" +
                "  \"dataFim\": \"2025-11-30T12:00:00\",\n" +
                "  \"pontoColeta\": {\n" +
                "    \"logradouro\": \"Rua Álvaro Mendes\",\n" +
                "    \"numero\": \"74\",\n" +
                "    \"bairro\": \"Centro\",\n" +
                "    \"cep\": \"64000-000\"\n" +
                "  }\n" +
                "}";

        // Act & Assert
        mockMvc.perform(post("/coletas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/coletas/1")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.coletor.id").value(2))
                .andExpect(jsonPath("$.pontoColeta.logradouro").value("Rua Álvaro Mendes"))
                .andExpect(jsonPath("$.itensColeta").isArray())
                .andExpect(jsonPath("$.itensColeta").isEmpty());
    }
    @Test
    @DisplayName("Deve lançar ServletException quando serviço lançar exceção")
    void createColetas_serviceThrowsException_throwsServletException() throws Exception {
        Mockito.when(coletaService.createColetas(any(ColetaForm.class)))
                .thenThrow(new RuntimeException("Erro interno ao salvar coleta"));

        String requestJson = "{\n" +
                "  \"coletorId\": 2,\n" +
                "  \"dataInicio\": \"" + LocalDateTime.now().plusDays(1) + "\",\n" +
                "  \"dataFim\": \"" + LocalDateTime.now().plusDays(1).plusHours(3) + "\",\n" +
                "  \"pontoColeta\": {\n" +
                "    \"logradouro\": \"Rua A\",\n" +
                "    \"numero\": \"10\",\n" +
                "    \"bairro\": \"Centro\",\n" +
                "    \"cep\": \"64000-000\"\n" +
                "  }\n" +
                "}";

        org.junit.jupiter.api.Assertions.assertThrows(
                jakarta.servlet.ServletException.class,
                () -> mockMvc.perform(post("/coletas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andReturn()
        );
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando datas não forem futuras")
    void createColetas_invalidDates_returnsBadRequest() throws Exception {
        String requestJson = "{\n" +
                "  \"coletorId\": 2,\n" +
                "  \"dataInicio\": \"2024-10-30T09:00:00\",\n" +
                "  \"dataFim\": \"2024-10-30T12:00:00\",\n" +
                "  \"pontoColeta\": {\n" +
                "    \"logradouro\": \"Rua Teste\",\n" +
                "    \"numero\": \"100\",\n" +
                "    \"bairro\": \"Centro\",\n" +
                "    \"cep\": \"64000-000\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/coletas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /coletas - deve retornar todas as coletas com sucesso")
    void testFindAll() throws Exception {
        when(coletaService.findAll(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/coletas")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(coletaService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /coletas/por-bairro?bairro=Centro - deve retornar coletas filtradas por bairro")
    void testFindByBairro() throws Exception {
        when(coletaService.findByBairro(eq("Centro"), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/coletas/por-bairro")
                        .param("bairro", "Centro")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

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
                .andExpect(status().isOk());

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

}