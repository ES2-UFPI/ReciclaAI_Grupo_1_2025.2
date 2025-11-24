package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.form.beneficiamento.EventoBeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.EventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para EventoBeneficiamentoService")
class EventoBeneficiamentoServiceTest {

    @Mock
    private EventoBeneficiamentoRepository eventoBeneficiamentoRepository;
    @Mock
    private BeneficiamentoService beneficiamentoService;
    @Mock
    private ColetorService coletorService;
    @Mock
    private ItemInventarioService itemInventarioService;
    @Mock
    private EventoBeneficiamentoMapper eventoBeneficiamentoMapper;

    @InjectMocks
    private EventoBeneficiamentoService eventoBeneficiamentoService;

    private EventoBeneficiamento eventoBeneficiamento;
    private Beneficiamento beneficiamento;
    private Coletor coletor;

    @BeforeEach
    void setUp() {
        beneficiamento = new Beneficiamento();
        beneficiamento.setId(1L);

        coletor = new Coletor();
        coletor.setId(1L);

        eventoBeneficiamento = new EventoBeneficiamento();
        eventoBeneficiamento.setId(1L);
        eventoBeneficiamento.setBeneficiamento(beneficiamento);
        eventoBeneficiamento.setColetor(coletor);
        eventoBeneficiamento.setStatus(StatusBeneficiamentoEnum.AGENDADA);
        eventoBeneficiamento.setItens(new ArrayList<>());
    }

    @Nested
    @DisplayName("Testes para o método create")
    class CreateTest {
        @Test
        @DisplayName("Deve criar um evento de beneficiamento com sucesso")
        void create_Success() {
            EventoBeneficiamentoForm form = new EventoBeneficiamentoForm();
            form.setBeneficiamentoId(1L);
            form.setColetorId(1L);

            when(eventoBeneficiamentoRepository.existsByBeneficiamentoIdAndColetorId(1L, 1L)).thenReturn(false);
            when(beneficiamentoService.findEntityById(1L)).thenReturn(beneficiamento);
            when(coletorService.findEntityById(1L)).thenReturn(coletor);
            when(eventoBeneficiamentoMapper.toModel(form)).thenReturn(new EventoBeneficiamento());
            when(eventoBeneficiamentoRepository.save(any(EventoBeneficiamento.class))).thenReturn(eventoBeneficiamento);
            when(eventoBeneficiamentoMapper.toDTO(eventoBeneficiamento)).thenReturn(new EventoBeneficiamentoDTO());

            EventoBeneficiamentoDTO result = eventoBeneficiamentoService.create(form);

            assertThat(result).isNotNull();
            verify(eventoBeneficiamentoRepository).save(any(EventoBeneficiamento.class));
        }

        @Test
        @DisplayName("Deve lançar exceção se o agendamento já existir")
        void create_DuplicatedSchedule_ShouldThrowException() {
            EventoBeneficiamentoForm form = new EventoBeneficiamentoForm();
            form.setBeneficiamentoId(1L);
            form.setColetorId(1L);
            when(eventoBeneficiamentoRepository.existsByBeneficiamentoIdAndColetorId(1L, 1L)).thenReturn(true);

            RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> eventoBeneficiamentoService.create(form));

            assertEquals("Este coletor já agendou participação para este beneficiamento.", exception.getMessage());
            verify(eventoBeneficiamentoRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes para o método delete")
    class DeleteTest {
        @Test
        @DisplayName("Deve deletar o evento e debitar itens do inventário do coletor")
        void delete_Success() {
            when(eventoBeneficiamentoRepository.findById(1L)).thenReturn(Optional.of(eventoBeneficiamento));
            doNothing().when(eventoBeneficiamentoRepository).delete(eventoBeneficiamento);

            assertDoesNotThrow(() -> eventoBeneficiamentoService.delete(1L));

            verify(eventoBeneficiamentoRepository).delete(eventoBeneficiamento);
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar deletar evento CONCLUIDO")
        void delete_Concluido_ShouldThrowException() {
            eventoBeneficiamento.setStatus(StatusBeneficiamentoEnum.CONCLUIDA);
            when(eventoBeneficiamentoRepository.findById(1L)).thenReturn(Optional.of(eventoBeneficiamento));

            RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> eventoBeneficiamentoService.delete(1L));

            assertEquals("Não é possível cancelar um evento de beneficiamento que já foi concluído.", exception.getMessage());
            verify(eventoBeneficiamentoRepository, never()).delete(any());
        }
    }
}
