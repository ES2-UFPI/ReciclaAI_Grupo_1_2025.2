package br.ufpi.recicle_ai.service.eventoColeta;

import br.ufpi.recicle_ai.domain.dto.eventoColeta.EventoColetaDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusEventoColetaEnum;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.eventoColeta.ItemEventoColeta;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.EventoColetaMapper;
import br.ufpi.recicle_ai.repository.EventoColetaRepository;
import br.ufpi.recicle_ai.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do EventoColetaService - Método findAllByColetorId")
class EventoColetaServiceTest {

    @Mock
    private EventoColetaRepository eventoColetaRepository;

    @Mock
    private EventoColetaMapper eventoColetaMapper;

    @Mock
    private ColetaService coletaService;

    @Mock
    private ProdutorService produtorService;

    @Mock
    private ItemInventarioService itemInventarioService;

    @Mock
    private MoedasVerdesService moedasVerdesService;

    @InjectMocks
    private EventoColetaService eventoColetaService;

    private br.ufpi.recicle_ai.domain.model.Coletor coletor;
    private br.ufpi.recicle_ai.domain.model.Produtor produtor;
    private Coleta coleta;
    private EventoColeta eventoColeta1;
    private EventoColeta eventoColeta2;
    private EventoColetaDTO eventoColetaDTO1;
    private EventoColetaDTO eventoColetaDTO2;
    private ItemEventoColeta itemEventoColeta;
    private br.ufpi.recicle_ai.domain.model.item.Item item;

    @BeforeEach
    void setUp() {
        // Preparar dados de teste
        coletor = new br.ufpi.recicle_ai.domain.model.Coletor();
        coletor.setId(1L);
        coletor.setNome("Coletor Teste");

        produtor = new br.ufpi.recicle_ai.domain.model.Produtor();
        produtor.setId(2L);
        produtor.setNome("Produtor Teste");

        coleta = new Coleta();
        coleta.setId(1L);
        coleta.setColetor(coletor);

        item = new br.ufpi.recicle_ai.domain.model.item.Item();
        item.setId(1L);
        item.setNome("Garrafa PET");
        item.setUnidade("unidade");

        itemEventoColeta = new ItemEventoColeta();
        itemEventoColeta.setItem(item);
        itemEventoColeta.setQuantidade(1);
        itemEventoColeta.setEventoColeta(eventoColeta1);
        itemEventoColeta.setId(1L);

        eventoColeta1 = new EventoColeta();
        eventoColeta1.setId(1L);
        eventoColeta1.setColeta(coleta);
        eventoColeta1.setProdutor(produtor);
        eventoColeta1.setStatus(StatusEventoColetaEnum.AGENDADA);
        List<ItemEventoColeta> itens = new ArrayList<>();
        itens.add(itemEventoColeta);
        eventoColeta1.setItens(itens);

        eventoColeta2 = new EventoColeta();
        eventoColeta2.setId(2L);
        eventoColeta2.setColeta(coleta);
        eventoColeta2.setProdutor(produtor);
        eventoColeta2.setStatus(StatusEventoColetaEnum.CONCLUIDA);

        eventoColetaDTO1 = new EventoColetaDTO();
        eventoColetaDTO1.setId(1L);
        eventoColetaDTO1.setStatus(StatusEventoColetaEnum.AGENDADA);

        eventoColetaDTO2 = new EventoColetaDTO();
        eventoColetaDTO2.setId(2L);
        eventoColetaDTO2.setStatus(StatusEventoColetaEnum.CONCLUIDA);
    }

    @Test
    @DisplayName("Deve retornar lista de DTOs quando coletor possui eventos")
    void deveBuscarEventosPorColetorIdComSucesso() {
        // Arrange
        Long coletorId = 1L;
        List<EventoColeta> eventosEntidade = Arrays.asList(eventoColeta1, eventoColeta2);

        when(eventoColetaRepository.findAllByColetaColetorId(coletorId)).thenReturn(eventosEntidade);
        when(eventoColetaMapper.toDTO(eventoColeta1)).thenReturn(eventoColetaDTO1);
        when(eventoColetaMapper.toDTO(eventoColeta2)).thenReturn(eventoColetaDTO2);

        // Act
        List<EventoColetaDTO> resultado = eventoColetaService.findAllByColetorId(coletorId);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(0).getStatus()).isEqualTo(StatusEventoColetaEnum.AGENDADA);
        assertThat(resultado.get(1).getId()).isEqualTo(2L);
        assertThat(resultado.get(1).getStatus()).isEqualTo(StatusEventoColetaEnum.CONCLUIDA);

        verify(eventoColetaRepository, times(1)).findAllByColetaColetorId(coletorId);
        verify(eventoColetaMapper, times(2)).toDTO(any(EventoColeta.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando coletor não possui eventos")
    void deveRetornarListaVaziaQuandoColetorNaoPossuiEventos() {
        // Arrange
        Long coletorId = 999L;
        when(eventoColetaRepository.findAllByColetaColetorId(coletorId)).thenReturn(Collections.emptyList());

        // Act
        List<EventoColetaDTO> resultado = eventoColetaService.findAllByColetorId(coletorId);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();

        verify(eventoColetaRepository, times(1)).findAllByColetaColetorId(coletorId);
        verify(eventoColetaMapper, never()).toDTO(any(EventoColeta.class));
    }

    @Test
    @DisplayName("Deve chamar o repository com o ID correto do coletor")
    void deveChamarRepositoryComIdCorreto() {
        // Arrange
        Long coletorId = 42L;
        when(eventoColetaRepository.findAllByColetaColetorId(coletorId)).thenReturn(Collections.emptyList());

        // Act
        eventoColetaService.findAllByColetorId(coletorId);

        // Assert
        verify(eventoColetaRepository, times(1)).findAllByColetaColetorId(coletorId);
        verify(eventoColetaRepository, times(1)).findAllByColetaColetorId(eq(42L));
    }

    @Test
    @DisplayName("Deve confirmar o evento com sucesso quando o status for AGENDADA")
    void confirmarEvento_ComStatusAgendada_DeveMudarStatusParaConcluida() {
        // Arrange
        when(eventoColetaRepository.findById(1L)).thenReturn(Optional.of(eventoColeta1));
        when(eventoColetaMapper.toDTO(eventoColeta1)).thenReturn(eventoColetaDTO2);

        // Act
        EventoColetaDTO resultado = eventoColetaService.confirmarEvento(1L);

        assertThat(resultado).isNotNull();
        verify(eventoColetaRepository).findById(1L);
        verify(eventoColetaRepository).save(eventoColeta1);
        assertEquals(StatusEventoColetaEnum.CONCLUIDA, eventoColeta1.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar confirmar evento já CONCLUIDA")
    void confirmarEvento_ComStatusConcluida_DeveLancarExcecao() {
        // Arrange
        eventoColeta1.setStatus(StatusEventoColetaEnum.CONCLUIDA);
        when(eventoColetaRepository.findById(1L)).thenReturn(Optional.of(eventoColeta1));

        // Act & Assert
        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            eventoColetaService.confirmarEvento(1L);
        });

        assertEquals("Este evento de coleta já está concluído.", exception.getMessage());
        verify(eventoColetaRepository, never()).save(any(EventoColeta.class));
    }
}
