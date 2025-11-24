package br.ufpi.recicle_ai.service.coleta;

import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.mapper.ColetaMapper;
import br.ufpi.recicle_ai.repository.ColetaRepository;
import br.ufpi.recicle_ai.service.ColetaService;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ColetaServiceTest {

    @Mock
    private ColetaRepository coletaRepository;

    @Mock
    private ColetaMapper coletaMapper;

    @InjectMocks
    private ColetaService coletaService;

    private Coleta coleta;
    private ColetaDTO coletaDTO;
    private Page<Coleta> pageColeta;
    private Pageable pageable;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        coleta = new Coleta();
        coleta.setId(1L);

        coletaDTO = new ColetaDTO();
        // Supondo que ColetaDTO tenha um setId ou que o mapper o configure
        // coletaDTO.setId(1L);

        pageable = PageRequest.of(0, 10);
        pageColeta = new PageImpl<>(List.of(coleta));
    }

    // ... outros testes ...

    @Test
    @DisplayName("findByColetor deve retornar página de DTOs corretamente")
    void testFindByColetor() {
        // Arrange
        when(coletaRepository.findAllByColetorIdOrderByDataInicioAsc(eq(5L), eq(pageable))).thenReturn(pageColeta);
        when(coletaMapper.toDTO(coleta)).thenReturn(coletaDTO);

        // Act
        Page<ColetaDTO> result = coletaService.findByColetor(5L, pageable);

        // Assert
        assertThat(result).isNotEmpty();
        verify(coletaRepository, times(1)).findAllByColetorIdOrderByDataInicioAsc(eq(5L), eq(pageable));
        verify(coletaMapper, times(1)).toDTO(coleta);
    }

    @Test
    @DisplayName("findByColetor deve retornar página vazia quando não houver coletas")
    void testFindByColetorEmpty() {
        // Arrange
        when(coletaRepository.findAllByColetorIdOrderByDataInicioAsc(eq(99L), eq(pageable))).thenReturn(Page.empty(pageable));

        // Act
        Page<ColetaDTO> result = coletaService.findByColetor(99L, pageable);

        // Assert
        assertThat(result).isEmpty();
        verify(coletaRepository, times(1)).findAllByColetorIdOrderByDataInicioAsc(eq(99L), eq(pageable));
        verifyNoInteractions(coletaMapper);
    }
    
    // ... outros testes ...
}
