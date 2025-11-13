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
        Long id = 1L;
        Beneficiamento beneficiamento = new Beneficiamento();
        beneficiamento.setId(id);
        BeneficiamentoDTO dto = new BeneficiamentoDTO();
        dto.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(beneficiamento));
        when(mapper.toDTO(beneficiamento)).thenReturn(dto);

        BeneficiamentoDTO resultado = service.findById(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    @DisplayName("Deve retornar página de Beneficiamentos por ID do Receptor")
    void deveRetornarPaginaDeBeneficiamentosPorReceptorId() {
        Long receptorId = 10L;
        Pageable pageable = PageRequest.of(0, 10);
        Beneficiamento b1 = new Beneficiamento();
        Page<Beneficiamento> paginaEntidades = new PageImpl<>(List.of(b1));
        when(repository.findAllByReceptorIdOrderByDataInicioAsc(receptorId, pageable)).thenReturn(paginaEntidades);

        Page<BeneficiamentoDTO> resultado = service.findByReceptor(receptorId, pageable);

        assertThat(resultado).isNotNull();
        verify(repository).findAllByReceptorIdOrderByDataInicioAsc(receptorId, pageable);
        verify(mapper, atLeastOnce()).toDTO(any(Beneficiamento.class));
    }

    @Test
    @DisplayName("Deve retornar página de Beneficiamentos por Bairro")
    void deveRetornarPaginaDeBeneficiamentosPorBairro() {
        String bairro = "Centro";
        Pageable pageable = PageRequest.of(0, 10);
        Beneficiamento b1 = new Beneficiamento();
        Page<Beneficiamento> paginaEntidades = new PageImpl<>(List.of(b1));
        when(repository.findAllByPontoColetaBairroContainingIgnoreCaseOrderByDataInicioAsc(bairro, pageable)).thenReturn(paginaEntidades);

        Page<BeneficiamentoDTO> resultado = service.findByBairro(bairro, pageable);

        assertThat(resultado).isNotNull();
        verify(repository).findAllByPontoColetaBairroContainingIgnoreCaseOrderByDataInicioAsc(bairro, pageable);
        verify(mapper, atLeastOnce()).toDTO(any(Beneficiamento.class));
    }
}
