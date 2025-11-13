package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.BeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.BeneficiamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Deve retornar DTO de Beneficiamento quando encontrar por ID")
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
    }

    @Test
    @DisplayName("Deve retornar página de Beneficiamentos por ID do Receptor")
    void deveRetornarPaginaDeBeneficiamentosPorReceptorId() {
        // Arrange
        Long receptorId = 10L;
        Pageable pageable = PageRequest.of(0, 10);
        Beneficiamento b1 = new Beneficiamento();
        b1.setId(1L);
        Page<Beneficiamento> paginaEntidades = new PageImpl<>(List.of(b1));

        when(repository.findAllByReceptorIdOrderByDataInicioAsc(receptorId, pageable)).thenReturn(paginaEntidades);

        // Act
        Page<BeneficiamentoDTO> resultado = service.findByReceptor(receptorId, pageable);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotalElements()).isEqualTo(1);
        verify(repository, times(1)).findAllByReceptorIdOrderByDataInicioAsc(receptorId, pageable);
        verify(mapper, times(1)).toDTO(any(Beneficiamento.class));
    }
}
