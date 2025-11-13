package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.BeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.BeneficiamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BeneficiamentoServiceTest {

    @Mock
    private BeneficiamentoRepository repository;

    @Mock
    private BeneficiamentoMapper mapper;

    @InjectMocks
    private BeneficiamentoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarBeneficiamentoDTOQuandoEncontrarPorId() {
        // Arrange
        Long id = 1L;
        Beneficiamento beneficiamento = new Beneficiamento();
        beneficiamento.setId(id);

        BeneficiamentoDTO dto = new BeneficiamentoDTO();
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(beneficiamento));
        when(mapper.toDTO(beneficiamento)).thenReturn(dto);

        // Act
        BeneficiamentoDTO resultado = service.findById(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDTO(beneficiamento);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarBeneficiamentoPorId() {
        // Arrange
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RegraDeNegocioException ex = assertThrows(
                RegraDeNegocioException.class,
                () -> service.findById(id)
        );

        assertEquals("Beneficiamento não encontrado!", ex.getMessage());
        verify(repository, times(1)).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void deveRetornarEntidadeBeneficiamentoQuandoEncontrarPorId() {
        // Arrange
        Long id = 5L;
        Beneficiamento beneficiamento = new Beneficiamento();
        beneficiamento.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(beneficiamento));

        // Act
        Beneficiamento resultado = service.findEntityById(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarEntidadeBeneficiamento() {
        // Arrange
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RegraDeNegocioException.class, () -> service.findEntityById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void deveRetornarListaDeBeneficiamentosPorReceptorId() {
        // Arrange
        Long receptorId = 10L;
        Beneficiamento b1 = new Beneficiamento();
        b1.setId(1L);
        Beneficiamento b2 = new Beneficiamento();
        b2.setId(2L);

        BeneficiamentoDTO dto1 = new BeneficiamentoDTO();
        dto1.setId(1L);
        BeneficiamentoDTO dto2 = new BeneficiamentoDTO();
        dto2.setId(2L);

        List<Beneficiamento> listaEntidades = Arrays.asList(b1, b2);
        when(repository.findAllByReceptor_id(receptorId)).thenReturn(listaEntidades);
        when(mapper.toDTO(b1)).thenReturn(dto1);
        when(mapper.toDTO(b2)).thenReturn(dto2);

        // Act
        List<BeneficiamentoDTO> resultado = service.findEntityByReceptorId(receptorId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());

        verify(repository, times(1)).findAllByReceptor_id(receptorId);
        verify(mapper, times(1)).toDTO(b1);
        verify(mapper, times(1)).toDTO(b2);
    }
}

