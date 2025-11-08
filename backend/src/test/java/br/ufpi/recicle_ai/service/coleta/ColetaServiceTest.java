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
    private Page<ColetaDTO> pageDTO;
    private Pageable pageable;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        coleta = new Coleta();
        coleta.setId(1L);

        coletaDTO = new ColetaDTO();
        // suponha que ColetaDTO tenha setId()
        // coletaDTO.setId(1L);

        pageable = PageRequest.of(0, 10);
        pageColeta = new PageImpl<>(List.of(coleta));
        pageDTO = new PageImpl<>(List.of(coletaDTO));
    }

    // ---------------------------
    // TESTE: findAll()
    // ---------------------------
    @Test
    @DisplayName("findAll deve retornar página de ColetaDTO corretamente")
    void testFindAll() {
        when(coletaRepository.findAll(pageable)).thenReturn(pageColeta);
        when(coletaMapper.toDTO(coleta)).thenReturn(coletaDTO);

        Page<ColetaDTO> result = coletaService.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(coletaRepository, times(1)).findAll(pageable);
        verify(coletaMapper, times(1)).toDTO(coleta);
    }

    @Test
    @DisplayName("findAll deve retornar página vazia quando repositório não tiver dados")
    void testFindAllEmpty() {
        when(coletaRepository.findAll(pageable)).thenReturn(Page.empty(pageable));

        Page<ColetaDTO> result = coletaService.findAll(pageable);

        assertThat(result).isEmpty();
        verify(coletaRepository, times(1)).findAll(pageable);
        verifyNoInteractions(coletaMapper);
    }

    // ---------------------------
    // TESTE: findEntityById()
    // ---------------------------
    @Test
    @DisplayName("findEntityById deve retornar a entidade quando encontrada")
    void testFindEntityByIdSuccess() {
        when(coletaRepository.findById(1L)).thenReturn(Optional.of(coleta));

        Coleta result = coletaService.findEntityById(1L);

        assertThat(result).isEqualTo(coleta);
        verify(coletaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findEntityById deve lançar exceção quando a coleta não existir")
    void testFindEntityByIdNotFound() {
        when(coletaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> coletaService.findEntityById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Coleta não encontrada!");

        verify(coletaRepository, times(1)).findById(1L);
    }

    // ---------------------------
    // TESTE: findByBairro()
    // ---------------------------
    @Test
    @DisplayName("findByBairro deve retornar página de DTOs corretamente")
    void testFindByBairro() {
        when(coletaRepository.findAllByPontoColetaBairroContainingIgnoreCase(eq("Centro"), eq(pageable)))
                .thenReturn(pageColeta);
        when(coletaMapper.toDTO(coleta)).thenReturn(coletaDTO);

        Page<ColetaDTO> result = coletaService.findByBairro("Centro", pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0)).isEqualTo(coletaDTO);
        verify(coletaRepository, times(1))
                .findAllByPontoColetaBairroContainingIgnoreCase(eq("Centro"), eq(pageable));
        verify(coletaMapper, times(1)).toDTO(coleta);
    }

    @Test
    @DisplayName("findByBairro deve retornar página vazia quando não houver resultados")
    void testFindByBairroEmpty() {
        when(coletaRepository.findAllByPontoColetaBairroContainingIgnoreCase(eq("Norte"), eq(pageable)))
                .thenReturn(Page.empty(pageable));

        Page<ColetaDTO> result = coletaService.findByBairro("Norte", pageable);

        assertThat(result).isEmpty();
        verify(coletaRepository, times(1))
                .findAllByPontoColetaBairroContainingIgnoreCase(eq("Norte"), eq(pageable));
        verifyNoInteractions(coletaMapper);
    }

    // ---------------------------
    // TESTE: findByColetor()
    // ---------------------------
    @Test
    @DisplayName("findByColetor deve retornar página de DTOs corretamente")
    void testFindByColetor() {
        when(coletaRepository.findAllByColetor_id(eq(5L), eq(pageable))).thenReturn(pageColeta);
        when(coletaMapper.toDTO(coleta)).thenReturn(coletaDTO);

        Page<ColetaDTO> result = coletaService.findByColetor(5L, pageable);

        assertThat(result).isNotEmpty();
        verify(coletaRepository, times(1)).findAllByColetor_id(eq(5L), eq(pageable));
        verify(coletaMapper, times(1)).toDTO(coleta);
    }

    @Test
    @DisplayName("findByColetor deve retornar página vazia quando não houver coletas")
    void testFindByColetorEmpty() {
        when(coletaRepository.findAllByColetor_id(eq(99L), eq(pageable))).thenReturn(Page.empty(pageable));

        Page<ColetaDTO> result = coletaService.findByColetor(99L, pageable);

        assertThat(result).isEmpty();
        verify(coletaRepository, times(1)).findAllByColetor_id(eq(99L), eq(pageable));
        verifyNoInteractions(coletaMapper);
    }
}

