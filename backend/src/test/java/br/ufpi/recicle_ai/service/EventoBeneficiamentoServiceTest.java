package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.mapper.EventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoBeneficiamentoServiceTest {

    @Mock
    private EventoBeneficiamentoRepository repository;

    @Mock
    private EventoBeneficiamentoMapper mapper;

    @InjectMocks
    private EventoBeneficiamentoService service;

    private EventoBeneficiamento eventoMock;
    private EventoBeneficiamentoDTO dtoMock;
    private PontoColeta pontoColetaMock;
    private Beneficiamento beneficiamentoMock;

    @BeforeEach
    void setUp() {
        pontoColetaMock = new PontoColeta();
        pontoColetaMock.setId(1L);
        pontoColetaMock.setBairro("Centro");
        pontoColetaMock.setLogradouro("Rua A");
        pontoColetaMock.setNumero("123");
        pontoColetaMock.setCep("64000-000");

        beneficiamentoMock = new Beneficiamento();
        beneficiamentoMock.setId(1L);
        beneficiamentoMock.setPontoColeta(pontoColetaMock);

        eventoMock = new EventoBeneficiamento();
        eventoMock.setId(1L);
        eventoMock.setBeneficiamento(beneficiamentoMock);
        eventoMock.setStatus(StatusBeneficiamentoEnum.AGENDADA);

        dtoMock = new EventoBeneficiamentoDTO();
        dtoMock.setId(1L);
        dtoMock.setStatus(StatusBeneficiamentoEnum.AGENDADA);
    }

    @Test
    @DisplayName("Deve retornar lista de eventos quando buscar por bairro existente")
    void testFindByBairro_Success() {
        // Arrange
        String bairro = "Centro";
        List<EventoBeneficiamento> eventosMock = Arrays.asList(eventoMock);
        
        when(repository.findByBairro(bairro)).thenReturn(eventosMock);
        when(mapper.toDTO(any(EventoBeneficiamento.class))).thenReturn(dtoMock);

        // Act
        List<EventoBeneficiamentoDTO> result = service.findByBairro(bairro);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repository, times(1)).findByBairro(bairro);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver eventos no bairro")
    void testFindByBairro_Empty() {
        // Arrange
        String bairro = "BairroInexistente";
        when(repository.findByBairro(bairro)).thenReturn(Arrays.asList());

        // Act
        List<EventoBeneficiamentoDTO> result = service.findByBairro(bairro);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByBairro(bairro);
    }
}
